package com.example.orderingroutingservice.workers

import com.example.orderingroutingservice.services.Auth0
import com.netflix.conductor.client.worker.Worker
import com.netflix.conductor.common.metadata.tasks.Task
import com.netflix.conductor.common.metadata.tasks.TaskResult
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status
import org.springframework.web.reactive.function.client.WebClient
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@JsonIgnoreProperties(ignoreUnknown = true)
data class Answer(
    val id: String = "",
    val text: String = ""
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Question(
    val answers: List<Answer> = emptyList(),
    val contextPk: Int = 0,
    val contextType: String = "",
    val existingAnswers: List<String> = emptyList(),
    val followUpQuestions: List<Any?> = emptyList(),
    val inputType: String = "",
    val isPersonProband: Boolean = false,
    val isPrincipalProband: Boolean = false,
    val likelihood: String = "",
    val personPk: Int = 0,
    val principalPk: Int = 0,
    val questionType: String = "",
    val requestedField: String = "",
    val requestedFieldConfigId: Int = 0,
    val satisfiableAnswer: String = "",
    val text: String = ""
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RQPlan(
    val questions: List<Question> = emptyList()
)

@Component
class ORSWorker(
    @Autowired val auth0Client: Auth0,
    @Value("\${ops.api-url}") val opsUrl: String
): Worker {

    override fun getTaskDefName(): String {
        return "ors"
    }

    override fun execute(task: Task?): TaskResult {
        val token = auth0Client.jwt // token we can use to hit
        val client = WebClient.create(opsUrl)

        val response = client.get()
            .uri("/requisitions/${task?.inputData?.get("rqid")}/plan")
            .header("Authorization", "Bearer $token")
            .exchangeToMono { res ->
                when (res.statusCode()) {
                    HttpStatus.OK -> res.bodyToMono(RQPlan::class.java)
                    else -> res.createException().flatMap { Mono.error(it) }
                }
            }
            .block()

        val result = TaskResult(task)
        result.status = Status.COMPLETED

        // Register the output of the task
        result.outputData["result"] = response

        return result
    }
}
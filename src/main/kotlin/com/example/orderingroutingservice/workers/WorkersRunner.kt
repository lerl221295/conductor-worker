package com.example.orderingroutingservice.workers

import com.netflix.conductor.client.automator.TaskRunnerConfigurer
import com.netflix.conductor.client.http.TaskClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class WorkersRunner(
    @Autowired var orsWorker: ORSWorker,
    @Value("\${conductor-url}") conductorUrl: String
) {
    init {
        val taskClient = TaskClient()
        taskClient.setRootURI(conductorUrl)
        val configurer: TaskRunnerConfigurer = TaskRunnerConfigurer.Builder(taskClient, listOf(orsWorker))
            .build()
        configurer.init()
    }
}
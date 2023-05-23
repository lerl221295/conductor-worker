package com.example.orderingroutingservice.services
import com.auth0.client.auth.AuthAPI
import com.auth0.json.auth.TokenHolder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date

@Service
class Auth0(
    @Value("\${auth0.domain}") val auth0Domain: String,
    @Value("\${auth0.client-id}") val auth0Client: String,
    @Value("\${auth0.client-secret}") val auth0Secret: String,
    @Value("\${auth0.audience}") val auth0Audience: String,
) {
    val authAPI: AuthAPI by lazy {
        AuthAPI.newBuilder(auth0Domain, auth0Client, auth0Secret).build()
    }

    val jwt: String
        get() {
            if (!::cachedTokenHolder.isInitialized || cachedTokenHolder.expiresAt.before(Date())) {
                // token is not initialized or expired
                cachedTokenHolder = authAPI.requestToken(auth0Audience)
                    .execute()
                    .body
            }
            return cachedTokenHolder.accessToken
        }

    private lateinit var cachedTokenHolder: TokenHolder
}
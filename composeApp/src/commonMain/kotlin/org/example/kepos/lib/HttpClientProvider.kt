package org.example.kepos.lib

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class AppError(message: String) : Throwable(message)

@Serializable
data class ErrorResponse(val message: String)

object AuthManager {
    private val token: String?
        get() = TokenStorage.getToken()

    private var onSignOutCallback: (() -> Unit)? = null

    fun setToken(newToken: String) {
        TokenStorage.saveToken(newToken)
    }

    fun clearToken() {
        TokenStorage.saveToken(null.toString())
    }

    fun registerSignOutCallback(callback: () -> Unit) {
        onSignOutCallback = callback
    }

    internal fun applyToken(builder: DefaultRequest.DefaultRequestBuilder) {
        token?.let {
            println("🔐 Adicionando token: $it")
            builder.headers {
                append(HttpHeaders.Authorization, "Bearer $it")
            }
        } ?: println("❌ Token está nulo!")
    }

    internal fun handleAuthError(status: HttpStatusCode, message: String?) {
        if (status == HttpStatusCode.Unauthorized ||
            status == HttpStatusCode.Forbidden ||
            message == "Usuário não encontrado no sistema"
        ) {
            clearToken()
            onSignOutCallback?.invoke()
        }
    }
}


fun createHttpClient(onSignOut: () -> Unit): HttpClient {
    AuthManager.registerSignOutCallback(onSignOut)

    return HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }

        install(Logging) {
            level = LogLevel.BODY
        }

        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTP
                host = "localhost"
                port = 8080
            }
            AuthManager.applyToken(this)
        }

        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, _ ->
                if (exception is ClientRequestException) {
                    val response = exception.response
                    val status = response.status
                    val text = response.bodyAsText()
                    val message = runCatching {
                        Json.decodeFromString<ErrorResponse>(text).message
                    }.getOrNull()

                    AuthManager.handleAuthError(status, message)

                    throw AppError(message ?: "Erro desconhecido")
                }

                throw exception
            }
        }
    }
}

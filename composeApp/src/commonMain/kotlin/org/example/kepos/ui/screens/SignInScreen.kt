import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.call.body
import io.ktor.client.plugins.*
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import org.example.kepos.lib.AppError
import org.example.kepos.lib.createHttpClient
import org.example.kepos.ui.components.CustomInputText
import org.example.kepos.ui.components.GreenButton
import org.example.kepos.ui.themes.KeposColors
import org.example.kepos.lib.TokenStorage

@Serializable
data class SigninRequest(val email: String, val senha: String)

@Serializable
data class SigninResponse(val token: String)

@Composable
fun SignInScreen(
    onSignInSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var warning by remember { mutableStateOf<String?>(null) }
    var message by remember { mutableStateOf<String?>(null) }

    val client = remember { createHttpClient { } }

    LaunchedEffect(Unit) {
        val existingToken = TokenStorage.getToken()
        if (!existingToken.isNullOrBlank()) {
            onSignInSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Fazer Login",
            fontSize = 40.sp,
            color = KeposColors.Green600
        )

        Spacer(modifier = Modifier.height(50.dp))

        CustomInputText(
            value = email,
            onValueChange = { email = it },
            placeholder = "E-mail / Usuário"
        )

        CustomInputText(
            value = password,
            onValueChange = { password = it },
            placeholder = "Senha",
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(30.dp))

        GreenButton(
            text = "Entrar",
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    message = "Preencha todos os campos obrigatórios."
                    return@GreenButton
                }

                warning = null
                message = null

                CoroutineScope(Dispatchers.Default).launch {
                    try {
                        val response = client.post("/api/v1/usuario/login") {
                            contentType(ContentType.Application.Json)
                            setBody(SigninRequest(email, password))
                        }

                        if (response.status.value in 200..299) {
                            val token = response.body<SigninResponse>().token
                            TokenStorage.saveToken(token)
                            onSignInSuccess()
                        } else {
                            val errorBody = response.bodyAsText()
                            warning = when (response.status.value) {
                                401 -> "E-mail ou senha incorretos."
                                else -> "Erro ao fazer login: ${extractMessageFromJson(errorBody)}"
                            }
                        }

                    } catch (e: ClientRequestException) {
                        val statusCode = e.response.status.value
                        val bodyText = e.response.bodyAsText()

                        warning = when (statusCode) {
                            401 -> "E-mail ou senha incorretos."
                            400 -> extractMessageFromJson(bodyText)
                            else -> "Erro de autenticação: ${extractMessageFromJson(bodyText)}"
                        }

                    } catch (e: ServerResponseException) {
                        val bodyText = e.response.bodyAsText()
                        warning = "Erro no servidor: ${extractMessageFromJson(bodyText)}"

                    } catch (e: RedirectResponseException) {
                        val bodyText = e.response.bodyAsText()
                        warning = "Erro de redirecionamento: ${extractMessageFromJson(bodyText)}"

                    } catch (e: ResponseException) {
                        val bodyText = e.response.bodyAsText()
                        warning = "Erro de resposta: ${extractMessageFromJson(bodyText)}"

                    } catch (e: AppError) {
                        warning = "Erro da aplicação: ${e.message}"

                    } catch (e: Exception) {
                        warning = "Erro inesperado: ${e.message ?: "Erro desconhecido"}"
                    }

                }

            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        warning?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFEAEA), shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(it, color = Color(0xFFB00020), fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        message?.let {
            Text(it, fontSize = 14.sp, color = KeposColors.Gray100)
            Spacer(modifier = Modifier.height(20.dp))
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Esqueci minha senha", color = KeposColors.Green600)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Criar conta",
                color = KeposColors.Green600,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onNavigateToSignUp() }
            )
        }
    }
}

fun extractMessageFromJson(jsonString: String): String {
    return try {
        val jsonElement = Json.parseToJsonElement(jsonString)
        when (jsonElement) {
            is JsonObject -> jsonElement["message"]?.jsonPrimitive?.content
            is JsonArray -> jsonElement.firstOrNull()?.jsonObject?.get("message")?.jsonPrimitive?.content
            else -> null
        } ?: "Mensagem não encontrada"
    } catch (e: Exception) {
        "Erro ao interpretar resposta do servidor"
    }
}

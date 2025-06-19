import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.kepos.lib.AppError
import org.example.kepos.lib.createHttpClient
import org.example.kepos.lib.TokenStorage
import org.example.kepos.ui.components.CustomInputText
import org.example.kepos.ui.components.GreenButton
import org.example.kepos.ui.themes.KeposColors
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

// Dados da requisição de cadastro
@Serializable
data class SignUpRequest(val nome: String, val email: String, val senha: String)

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confPassword by remember { mutableStateOf("") }
    var warning by remember { mutableStateOf<String?>(null) }

    val client = remember { createHttpClient { /* onSignOut */ } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Criar uma conta",
            fontSize = 32.sp,
            color = KeposColors.Green600
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomInputText(
            value = username,
            onValueChange = {
                username = it
                warning = null
            },
            placeholder = "Usuário"
        )

        CustomInputText(
            value = email,
            onValueChange = {
                email = it
                warning = null
            },
            placeholder = "E-mail"
        )

        CustomInputText(
            value = password,
            onValueChange = {
                password = it
                warning = null
            },
            placeholder = "Senha"
        )

        CustomInputText(
            value = confPassword,
            onValueChange = {
                confPassword = it
                warning = null
            },
            placeholder = "Confirmar senha"
        )

        warning?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = it,
                color = KeposColors.Red,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        GreenButton(
            text = "Criar Conta",
            onClick = {
                if (username.isBlank() || email.isBlank() || password.isBlank() || confPassword.isBlank()) {
                    warning = "Preencha todos os campos"
                    println("⚠️ Campos obrigatórios não preenchidos")
                } else if (password != confPassword) {
                    warning = "Senhas não coincidem"
                    password = ""
                    confPassword = ""
                    println("❌ Senhas diferentes")
                } else {
                    warning = null
                    println("🔃 Enviando requisição de cadastro...")

                    CoroutineScope(Dispatchers.Default).launch {
                        try {
                            val response = client.post("/api/v1/usuario") {
                                contentType(ContentType.Application.Json)
                                setBody(SignUpRequest(username, email, password))
                            }

                            if (response.status.value in 200..299) {
                                println("✅ Cadastro feito com sucesso!")

                                try {
                                    val loginResponse = client.post("/api/v1/usuario/login") {
                                        contentType(ContentType.Application.Json)
                                        setBody(SigninRequest(email, password))
                                    }

                                    if (loginResponse.status.value in 200..299) {
                                        val token = loginResponse.body<SigninResponse>().token
                                        TokenStorage.saveToken(token)
                                        println("✅ Login automático feito com sucesso!")
                                        onSignUpSuccess()
                                    } else {
                                        val errorBody = loginResponse.bodyAsText()
                                        warning = "Erro no login após cadastro: $errorBody"
                                        println("⚠️ Falha no login automático: $errorBody")
                                    }

                                } catch (e: ResponseException) {
                                    val error = e.response.bodyAsText()
                                    warning = "Erro ao logar: $error"
                                    println("❌ Erro ao logar: $error")
                                }

                            } else {
                                val errorText = response.bodyAsText()
                                warning = "Erro no cadastro: $errorText"
                                println("❌ Erro no cadastro: $errorText")
                            }

                        } catch (e: AppError) {
                            println("❌ AppError: ${e.message}")
                            warning = e.message

                        } catch (e: ResponseException) {
                            val errorText = e.response.bodyAsText()
                            println("❌ ResponseException: ${errorText}")
                            warning = "Erro: $errorText"

                        } catch (e: Exception) {
                            e.printStackTrace()
                            warning = "Erro inesperado: ${e.message ?: "Erro desconhecido"}"
                            println("❌ Erro inesperado: ${e.message}")
                        }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Já possuo uma conta",
            color = KeposColors.Green600,
            modifier = Modifier.clickable {
                onNavigateToSignIn()
            }
        )
    }
}

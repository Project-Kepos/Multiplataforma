package org.example.kepos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import org.example.kepos.lib.AppError
import org.example.kepos.lib.createHttpClient
import org.example.kepos.lib.TokenStorage

@Serializable
data class UpdateUserRequest(val nome: String, val email: String, val senha: String?)

@Composable
fun MyAccountScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    var editMode by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }

    val client = remember { createHttpClient {} }

    // Buscar dados do usuário ao montar
    LaunchedEffect(Unit) {
        try {
            val response = client.get("/api/v1/usuario").body<JsonObject>()
            nome = response["nome"]?.jsonPrimitive?.content ?: ""
            email = response["email"]?.jsonPrimitive?.content ?: ""
        } catch (e: Exception) {
            message = "Erro ao carregar dados do usuário. ${TokenStorage.getToken()}"

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Minha Conta", fontSize = 30.sp, color = Color(0xFF2E7D32))

        Spacer(modifier = Modifier.height(24.dp))

        CustomInputText(
            value = nome,
            onValueChange = { nome = it },
            placeholder = "Nome",
            enabled = editMode
        )

        CustomInputText(
            value = email,
            onValueChange = { email = it },
            placeholder = "Email",
            enabled = editMode
        )

        if (editMode) {
            CustomInputText(
                value = senha,
                onValueChange = { senha = it },
                placeholder = "Nova senha (opcional)",
                isPassword = true
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            GreenButton("Voltar") { onBack() }

            if (editMode) {
                GreenButton("Salvar") {
                    CoroutineScope(Dispatchers.Default).launch {
                        try {
                            client.put("/api/v1/usuario") {
                                contentType(ContentType.Application.Json)
                                setBody(UpdateUserRequest(nome, email, senha.ifBlank { null }))
                            }
                            message = "Cadastro atualizado com sucesso."
                            editMode = false
                        } catch (e: AppError) {
                            message = e.message
                        } catch (e: Exception) {
                            message = "Erro inesperado. Tente novamente."
                        }
                    }
                }
            } else {
                GreenButton("Editar") { editMode = true }
            }

            GreenButton("Sair") {
                TokenStorage.removeToken()
                onLogout()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        message?.let {
            Text(it, color = Color.Red, fontSize = 14.sp)
        }
    }
}

@Composable
fun CustomInputText(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    enabled: Boolean = true,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(placeholder) },
        singleLine = true,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
    )
}

@Composable
fun GreenButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2E7D32)),
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
    ) {
        Text(text, color = Color.White)
    }
}

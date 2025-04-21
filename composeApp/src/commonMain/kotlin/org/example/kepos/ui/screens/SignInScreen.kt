package org.example.kepos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.kepos.ui.components.CustomInputText
import org.example.kepos.ui.components.GreenButton
import org.example.kepos.ui.themes.KeposColors

@Composable
fun SignInScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Fazer Login",
            fontSize = 32.sp,
            color = KeposColors.Green600
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomInputText(
            value = email,
            onValueChange = { email = it },
            placeholder = "E-mail / Usuário"
        )

        CustomInputText(
            value = password,
            onValueChange = { password = it },
            placeholder = "Senha"
        )

        GreenButton(
            text = "Entrar",
            onClick = {
                message = if (email.isNotBlank() && password.isNotBlank()) {
                    "Login enviado (simulado)"
                } else {
                    "Preencha todos os campos"
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(0.6f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Esqueci minha senha",
                color = KeposColors.Green600
            )
            Text(
                text = "Criar conta",
                color = KeposColors.Green600
            )
        }

        message?.let {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = it,
                fontSize = 14.sp,
                color = KeposColors.Gray100
            )
        }
    }
}

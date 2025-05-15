package org.example.kepos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.kepos.ui.components.CustomInputText
import org.example.kepos.ui.components.GreenButton
import org.example.kepos.ui.themes.KeposColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import org.example.kepos.ui.components.AddDendroBox
import org.example.kepos.ui.components.DendroBox


@Composable
fun SignInScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }

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
            placeholder = "Senha"
        )

        Spacer(modifier = Modifier.height(30.dp))

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

        Spacer(modifier = Modifier.height(20.dp))

        // Centralizando os dois links
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Esqueci minha senha",
                color = KeposColors.Green600
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Criar conta",
                color = KeposColors.Green600
            )
        }

        message?.let {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = it,
                fontSize = 14.sp,
                color = KeposColors.Gray100
            )
        }
        DendroBox("asd","w")
        AddDendroBox()
    }
}


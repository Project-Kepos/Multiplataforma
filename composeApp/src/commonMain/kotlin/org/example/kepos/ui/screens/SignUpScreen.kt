package org.example.kepos.ui.screens

import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.kepos.ui.components.CustomInputText
import org.example.kepos.ui.components.GreenButton
import org.example.kepos.ui.themes.KeposColors
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment

@Composable
fun SignUpScreen() {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confPassword by remember { mutableStateOf("") }
    var warning by remember { mutableStateOf<String?>(null) }

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
                } else if (password != confPassword) {
                    warning = "Senhas não coincidem"
                    password = ""
                    confPassword = ""
                } else {
                    warning = "Conta criada (simulado)"
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Já possuo uma conta",
            color = KeposColors.Green600,
            modifier = Modifier.clickable {
                // TODO: Navegar para tela de login
            }
        )
    }
}

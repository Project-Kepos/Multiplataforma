package org.example.kepos

import CustomInputText
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import keposmultiplataforma.composeapp.generated.resources.Res
import keposmultiplataforma.composeapp.generated.resources.compose_multiplatform
import org.example.kepos.ui.components.GreenButton

@Composable
@Preview
fun App() {
    MaterialTheme {

        var text by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomInputText(
                value = text,
                onValueChange = { text = it },
                placeholder = "Login"
            )
            CustomInputText(
                value = text,
                onValueChange = { text = it },
                placeholder = "Senha"
            )
            GreenButton("Entrar")
        }
    }
}
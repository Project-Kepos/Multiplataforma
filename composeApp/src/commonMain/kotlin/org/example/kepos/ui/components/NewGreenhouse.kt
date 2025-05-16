package org.example.kepos.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewGreenhouse() {
    var code by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
    ) {
        // Topo: título à esquerda e botão voltar à direita
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Adicionar estufa",
                fontSize = 32.sp,
                fontWeight = FontWeight.Light,
                color = Color(0xFF2F855A),
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = {
                println("Voltar pressionado")
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color(0xFF2F855A),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Voltar",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    color = Color(0xFF2F855A)
                )
            }
        }

        // Centro: instruções e campo de texto
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Insira o código de",
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
            Text(
                text = "12 dígitos da sua estufa",
                fontSize = 20.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = code,
                onValueChange = { code = it },
                placeholder = {
                    Text(
                        "ABCD-EFGH-IJKL-LMNP",
                        color = Color(0xFF9E9E9E),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.width(350.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF38A169),
                    unfocusedIndicatorColor = Color(0xFF38A169),
                    disabledIndicatorColor = Color.Gray,
                    cursorColor = Color(0xFF38A169)
                ),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Botão de buscar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = {
                    println("Código digitado: $code")
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFF2F855A)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Buscar",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

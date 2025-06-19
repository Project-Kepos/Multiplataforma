package org.example.kepos.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GreenhouseNotFound(onBack: () -> Unit, httpStatusCode: Int? = null) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.widthIn(max = 360.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Estufa não encontrada",
                tint = Color(0xFF38A169),
                modifier = Modifier.size(360.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Estufa não encontrada",
                fontWeight = FontWeight.W200,
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Erro HTTP: $httpStatusCode",
                    color = Color.Red
                )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color(0xFF38A169)
                ),
                elevation = null
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Voltar",
                    fontWeight = FontWeight.W200,
                    fontSize = 18.sp
                )
            }
        }
    }
}


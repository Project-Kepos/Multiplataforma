package org.example.kepos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.kepos.ui.themes.KeposColors

@Composable
fun GreenhouseAlreadyRegistered(
    onBackClick: () -> Unit = {},
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp)
            .wrapContentSize(align = Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Ícone de alerta
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Dendro já registrada",
            tint = KeposColors.Green500,
            modifier = Modifier
                .size(180.dp) // tamanho proporcional ao mobile/desktop
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Mensagem
        Text(
            text = "Déndro já associada com o usuário",
            fontSize = 18.sp,
            color = Color.Black
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
                text = "Tentar novamente",
                fontWeight = FontWeight.W200,
                fontSize = 18.sp
            )
        }


    }
}

package org.example.kepos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.kepos.ui.themes.KeposColors

@Composable
fun GreenhouseAlreadyRegistered(
    onBackClick: () -> Unit = {}
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


    }
}

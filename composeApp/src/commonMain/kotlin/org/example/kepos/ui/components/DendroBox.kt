package org.example.kepos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import org.example.kepos.ui.themes.KeposColors

@Composable
fun DendroBox(
    dendroName: String,
    onClick: String
) {
    // Simula um efeito ao pressionar (como hover em web)
    var isPressed by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(top = 10.dp)
            .widthIn(min = 179.dp)
            .height(179.dp)
            .border(2.dp, KeposColors.Green500, shape = RoundedCornerShape(4.dp))
            .background(
                color = if (isPressed) KeposColors.TransitionGreen else KeposColors.Green100,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable {
                isPressed = true
                //onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Dendro Icon",
                modifier = Modifier.size(40.dp),
                tint = KeposColors.Green500
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = dendroName,
                fontSize = 16.sp,
                color = KeposColors.Green500
            )
        }
    }
}

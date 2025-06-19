package org.example.kepos.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.kepos.ui.themes.KeposColors
// import androidx.navigation.NavController

// fun AddDendroBox(navController: NavController) {
@Composable
fun AddDendroBox(
    // By using a lambda, this component doesn't need to know
    // anything about navigation. It just reports that it was clicked.
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(top = 10.dp)
            .widthIn(min = 179.dp)
            .height(179.dp)
            .clickable { onClick() }, // Simply call the passed-in function
        contentAlignment = Alignment.Center
    ) {
        // ... The rest of the code (Canvas, Column) remains the same
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = KeposColors.Green500,
                size = size,
                style = Stroke(
                    width = 3f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 10f), 17f)
                ),
                cornerRadius = CornerRadius(4.dp.toPx())
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Adicionar Estufa", // Improved content description
                modifier = Modifier.size(40.dp),
                tint = KeposColors.Green500
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Adicionar\n   Estufa",
                fontSize = 16.sp,
                lineHeight = 20.sp,
                color = KeposColors.Green500,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


package org.example.kepos.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.*

@Composable
fun GreenButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor by animateColorAsState(
        targetValue = if (isPressed) Color(0xFFF5F5F5) else Color.White,
        animationSpec = tween(durationMillis = 1000)
    )

    val borderColor = Color(0xFF00A86B) // verde
    val textColor = Color(0xFF00A86B)

    Box(
        modifier = modifier
            .fillMaxWidth(0.6f)
            .border(2.dp, borderColor, RoundedCornerShape(4.dp))
            .background(backgroundColor, shape = RoundedCornerShape(4.dp))
            .clickable(enabled = onClick != null, interactionSource = interactionSource, indication = null) {
                onClick?.invoke()
            }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = textColor
        )
    }
}

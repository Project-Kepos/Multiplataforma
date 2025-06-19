package org.example.kepos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.kepos.ui.themes.KeposColors

@Composable
fun DendroBox(
    dendroName: String,
    // FIX 1: The onClick parameter must be a function type, not Unit.
    onClick: () -> Unit
) {
    // FIX 2: Use InteractionSource to properly handle press and release states.
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor = if (isPressed) {
        KeposColors.TransitionGreen
    } else {
        KeposColors.Green100
    }

    Box(
        modifier = Modifier
            .padding(top = 10.dp)
            .widthIn(min = 179.dp)
            .height(179.dp)
            .border(2.dp, KeposColors.Green500, shape = RoundedCornerShape(4.dp))
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(4.dp)
            )
            // FIX 3: Pass the interactionSource and the onClick lambda directly.
            .clickable(
                interactionSource = interactionSource,
                // Pass null for indication to disable the default ripple effect,
                // since we are using a custom background color change as feedback.
                indication = null,
                onClick = onClick
            ),
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
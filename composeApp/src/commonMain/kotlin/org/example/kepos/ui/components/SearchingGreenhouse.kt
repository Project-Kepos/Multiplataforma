import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchingGreenhouse(
    greenhouseCode: String = "ABCD-EFGH-IJKL"
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // Botão cancelar no canto superior direito
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.TopEnd),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cancelar"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Cancelar", fontSize = 18.sp)
        }

        // Conteúdo centralizado
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DotsLoader()

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Buscando Estufa", fontSize = 28.sp)
            Text(
                text = greenhouseCode,
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun DotsLoader() {
    val dotCount = 3
    val delayBetweenDots = 150L

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(dotCount) { index ->
            val scale by animateDotScale(delayMillis = index * delayBetweenDots)
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .scale(scale)
                    .background(Color(0xFF2F855A), CircleShape)
            )
        }
    }
}

@Composable
fun animateDotScale(delayMillis: Long): State<Float> {
    val infiniteTransition = rememberInfiniteTransition()
    return infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis.toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
}

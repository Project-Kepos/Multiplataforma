
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun GreenhouseScreen() {
    val greenhouse = remember {
        mutableStateOf(
            GreenhouseData(
                name = "Dendro 1",
                temperature = 25,
                humidity = 89,
                weather = "Ensolarado",
                modules = listOf(
                    ModuleData("Salsa", "Lorem ipsum", 89),
                    ModuleData("Manjericão", "Lorem ipsum", 89)
                )
            )
        )
    }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text("Estufa: ${greenhouse.value.name}", fontSize = 32.sp)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                InfoCard("Temperatura", "${greenhouse.value.temperature}°C")
                InfoCard("Umidade", "${greenhouse.value.humidity} g/m³")
                InfoCard("Clima", greenhouse.value.weather)
            }

            Text("Módulos", fontSize = 24.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                greenhouse.value.modules.forEach {
                    ModuleCard(it)
                }
                repeat(4 - greenhouse.value.modules.size) {
                    EmptySlot()
                }
            }

            Text("Configurações", fontSize = 24.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { }) { Text("Renomear") }
                Button(onClick = { }) { Text("Atualizar") }
                Button(onClick = { }) { Text("Desconectar") }
            }
        }
    }
}

@Composable
fun InfoCard(title: String, value: String) {
    Card(
        modifier = Modifier.size(150.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFFDDEEDD)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {
            Text(title, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(value, fontSize = 20.sp)
        }
    }
}

@Composable
fun ModuleCard(module: ModuleData) {
    Card(
        modifier = Modifier.size(150.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFFEEF7EE)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {
            Text(module.name, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("${module.humidity}g/m³", fontSize = 16.sp)
        }
    }
}

@Composable
fun EmptySlot() {
    Card(
        modifier = Modifier.size(150.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text("Slot Vazio", fontSize = 14.sp)
        }
    }
}

data class GreenhouseData(
    val name: String,
    val temperature: Int,
    val humidity: Int,
    val weather: String,
    val modules: List<ModuleData>
)

data class ModuleData(
    val name: String,
    val description: String,
    val humidity: Int
)

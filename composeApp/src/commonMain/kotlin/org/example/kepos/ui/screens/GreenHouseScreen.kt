import androidx.compose.foundation.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.util.network.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.example.kepos.lib.createHttpClient
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GreenhouseScreen(onBack: () -> Unit = {}, id: String) {
    val client = remember { createHttpClient { /* onSignOut */ } }
    var greenhouse by remember { mutableStateOf<GreenhouseData?>(null) }
    var newName by remember { mutableStateOf("") }
    var showRename by remember { mutableStateOf(false) }
    var showDisconnect by remember { mutableStateOf(false) }
    var showNewModule by remember { mutableStateOf(false) }
    var newModuleName by remember { mutableStateOf("") }
    var newModuleDesc by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(id) {
        try {
            val gh = client.get("/api/v1/dendro/$id").body<GreenhouseData>()
            greenhouse = gh
            newName = gh.name
        } catch (e: ClientRequestException) {
            val body = e.response.bodyAsText()
            errorMessage = "Erro ${e.response.status.value}: $body"
        } catch (e: ServerResponseException) {
            val body = e.response.bodyAsText()
            errorMessage = "Erro do servidor (${e.response.status.value}): $body"
        } catch (e: UnresolvedAddressException) {
            errorMessage = "Erro de conexão: ${e.message}"
        } catch (e: Exception) {
            errorMessage = "Erro inesperado: ${e.message}"
        } finally {
            loading = false
        }
    }

    if (loading || greenhouse == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                if (errorMessage != null) {
                    Spacer(Modifier.height(16.dp))
                    Text(errorMessage!!, color = Color.Red)
                }
            }
        }
        return
    }

    val gh = greenhouse!!

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Estufa: ${gh.name}", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Button(onClick = onBack) { Text("Voltar") }
        }

        Spacer(Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            InfoCard("Temperatura", "${gh.temperature}°C")
            InfoCard("Umidade", "${gh.humidity}%")
            InfoCard("Luminosidade", "${gh.luminosity}")
        }

        Spacer(Modifier.height(32.dp))

        Text("Módulos", fontSize = 24.sp, fontWeight = FontWeight.Medium)

        Spacer(Modifier.height(16.dp))

        val modulesPerRow = 4
        val modules = gh.modules
        val totalSlots = 4
        val rows = (modules + List(totalSlots - modules.size) { null }).chunked(modulesPerRow)

        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                row.forEach { module ->
                    if (module != null) {
                        ModuleCard(module)
                    } else {
                        EmptySlot { showNewModule = true }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }

        Spacer(Modifier.height(32.dp))

        Text("Configurações da Estufa", fontSize = 20.sp)

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { showRename = true }) {
                Text("Renomear")
            }
            Button(onClick = {
                scope.launch {
                    try {
                        val updated = client.get("/api/v1/dendro/$id").body<GreenhouseData>()
                        greenhouse = updated
                    } catch (e: Exception) {
                        errorMessage = "Erro ao atualizar: ${e.message}"
                    }
                }
            }) {
                Text("Atualizar")
            }
            Button(onClick = { showDisconnect = true }) {
                Text("Desconectar")
            }
        }

        if (showRename) {
            AlertDialog(
                onDismissRequest = { showRename = false },
                title = { Text("Renomear Estufa") },
                text = {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("Novo nome") }
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        greenhouse = gh.copy(name = newName)
                        showRename = false
                    }) { Text("Atualizar") }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showRename = false }) { Text("Cancelar") }
                }
            )
        }

        if (showDisconnect) {
            AlertDialog(
                onDismissRequest = { showDisconnect = false },
                title = { Text("Desconectar Estufa") },
                text = { Text("Deseja realmente desconectar a estufa?") },
                confirmButton = {
                    Button(onClick = {
                        showDisconnect = false
                        onBack()
                    }) { Text("Confirmar") }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showDisconnect = false }) { Text("Cancelar") }
                }
            )
        }

        if (showNewModule) {
            AlertDialog(
                onDismissRequest = { showNewModule = false },
                title = { Text("Cadastrar Módulo") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = newModuleName,
                            onValueChange = { newModuleName = it },
                            label = { Text("Nome do módulo") }
                        )
                        OutlinedTextField(
                            value = newModuleDesc,
                            onValueChange = { newModuleDesc = it },
                            label = { Text("Descrição") }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        greenhouse = gh.copy(
                            modules = gh.modules + ModuleData(
                                name = newModuleName,
                                description = newModuleDesc,
                                humidity = 80.0
                            )
                        )
                        showNewModule = false
                        newModuleName = ""
                        newModuleDesc = ""
                    }) { Text("Cadastrar") }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showNewModule = false }) { Text("Cancelar") }
                }
            )
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(module.name, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("${module.humidity} g/m³", fontSize = 16.sp)
        }
    }
}

@Composable
fun EmptySlot(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Slot Vazio", fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Serializable
data class GreenhouseData(
    val id: String,
    val name: String,
    val temperature: Double,
    val humidity: Double,
    val luminosity: Int,
    val weather: String = "",
    val modules: List<ModuleData> = emptyList()
)

@Serializable
data class ModuleData(
    val name: String,
    val description: String,
    val humidity: Double
)

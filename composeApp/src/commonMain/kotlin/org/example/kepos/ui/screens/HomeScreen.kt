package org.example.kepos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.example.kepos.ui.components.AddDendroBox
import org.example.kepos.ui.components.DendroBox
import org.example.kepos.ui.themes.KeposColors
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.example.kepos.lib.TokenStorage
import org.example.kepos.lib.createHttpClient

@Serializable
data class DendroResponse(val id: String, val name: String)

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onNavigateToAddGreenhouse: () -> Unit,
    onNavigateToGreenhouse: (dendroId: String) -> Unit,
    onNavigateToAccount: () -> Unit
) {
    val client = remember { createHttpClient { /* onSignOut */ } }
    var dendros by remember { mutableStateOf<List<DendroResponse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Carregar dados na primeira composição
    LaunchedEffect(Unit) {
        try {
            isLoading = true
            dendros = fetchUserDendros(client)
        } catch (e: Exception) {
            println("Erro ao carregar dendros: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = "Estufas pareadas",
                fontSize = 24.sp,
                color = KeposColors.Green600,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Configurações",
                tint = KeposColors.Green500,
                modifier = Modifier
                    .size(36.dp)
                    .clickable { onNavigateToAccount() }
            )
        }

        Divider(
            color = KeposColors.Green500,
            thickness = 3.dp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = KeposColors.Green500)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 180.dp),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(dendros) { dendro ->
                    DendroBox(
                        dendroName = dendro.name,
                        onClick = { onNavigateToGreenhouse(dendro.id) }
                    )
                }
                item {
                    AddDendroBox(onClick = onNavigateToAddGreenhouse)
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = {
                coroutineScope.launch {
                    try {
                        isLoading = true
                        dendros = fetchUserDendros(client)
                    } catch (e: Exception) {
                        println("Erro ao atualizar: ${e.message}")
                    } finally {
                        isLoading = false
                    }
                }
            }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Atualizar",
                        tint = KeposColors.Green500,
                        modifier = Modifier.size(46.dp)
                    )
                    Text("Atualizar")
                }
            }

            Spacer(modifier = Modifier.width(24.dp))

            IconButton(onClick = onLogout) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Desconectar",
                        tint = KeposColors.Green500,
                        modifier = Modifier.size(46.dp)
                    )
                    Text("Desconectar")
                }
            }
        }
    }
}

// Função para buscar estufas do usuário
suspend fun fetchUserDendros(client: HttpClient): List<DendroResponse> {
    println(TokenStorage.getToken())
    return client.get("/api/v1/dendro/usuario").body()
}

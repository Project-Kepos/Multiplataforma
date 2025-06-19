import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import org.example.kepos.lib.createHttpClient
import org.example.kepos.ui.components.GreenhouseNotFound
import org.example.kepos.ui.components.NewGreenhouse
import org.example.kepos.ui.screens.GreenhouseAlreadyRegistered

@Serializable
data class AddGreenhouseRequest(val id: String)

@Composable
fun AddGreenhouseScreen(onBack: () -> Unit) {
    var isSubmitting by remember { mutableStateOf(false) }
    var greenhouseWasFound by remember { mutableStateOf(false) }
    var greenhouseAlreadyRegistered by remember { mutableStateOf(false) }
    var errorWhileSearching by remember { mutableStateOf(false) }
    var idDendro by remember { mutableStateOf("") }
    var httpStatusCode by remember { mutableStateOf<Int?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val client = remember { createHttpClient { /* onSignOut */ } }
    val coroutineScope = rememberCoroutineScope()

    fun resetAllStates() {
        isSubmitting = false
        greenhouseWasFound = false
        greenhouseAlreadyRegistered = false
        errorWhileSearching = false
        httpStatusCode = null
        errorMessage = null
    }

    fun handleSearchGreenhouse() {
        resetAllStates()
        isSubmitting = true

        coroutineScope.launch {
            delay(2000)

            try {
                val response: HttpResponse = client.request("/api/v1/dendro/usuario") {
                    method = HttpMethod.Patch
                    contentType(ContentType.Application.Json)
                    setBody(AddGreenhouseRequest(id = idDendro))
                }

                greenhouseAlreadyRegistered = response.bodyAsText().contains("associado")

                if (response.status == HttpStatusCode.OK) {
                    greenhouseWasFound = true
                } else {
                    httpStatusCode = response.status.value
                    errorWhileSearching = true
                }

            } catch (e: Exception) {
                println("Erro: ${e::class.simpleName} - ${e.message}")
                e.printStackTrace()

                when (e) {
                    is ClientRequestException,
                    is ServerResponseException,
                    is ResponseException -> {
                        val res = (e as ResponseException).response
                        val bodyText = res.bodyAsText()
                        httpStatusCode = res.status.value
                        errorMessage = bodyText

                        if (bodyText.contains("A dendro já possui um usuário associado", ignoreCase = true)) {
                            greenhouseAlreadyRegistered = true
                        } else {
                            errorWhileSearching = true
                        }
                    }
                    else -> {
                        errorWhileSearching = true
                        errorMessage = e.message
                        httpStatusCode = null
                    }
                }
            } finally {
                isSubmitting = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color(0xFF38A169)
            ),
            elevation = null,
        ) {
            Text("← Voltar")
        }
/*
        if (errorWhileSearching) {
            Text(
                text = httpStatusCode?.let { "Erro HTTP $it" } ?: "Erro de rede ou desconhecido",
                color = Color.Red
            )
            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red
                )
            }
        }*/

        when {
            isSubmitting -> SearchingGreenhouse(
                idDendro = idDendro,
                onCancel = { resetAllStates() }
            )
            greenhouseWasFound -> GreenhouseFound()
            greenhouseAlreadyRegistered -> GreenhouseAlreadyRegistered()
            errorWhileSearching -> GreenhouseNotFound(onBack = { resetAllStates() })
            else -> NewGreenhouse(
                idDendro = idDendro,
                onIdDendroChange = { idDendro = it },
                onSearchGreenhouse = { handleSearchGreenhouse() }
            )
        }
    }
}

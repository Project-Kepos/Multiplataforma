package org.example.kepos.ui.screens

import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Close

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import org.example.kepos.ui.components.AddDendroBox
import org.example.kepos.ui.components.DendroBox
import org.example.kepos.ui.themes.KeposColors
// import androidx.navigation.NavController

@Composable
// fun HomeScreen(navController: NavController) {
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()  // ocupar toda a largura para empurrar o ícone pra direita
                .padding(bottom = 8.dp) // espaço entre texto e a borda
        ) {
            Text(
                text = "Estufas pareadas",
                fontSize = 24.sp,
                color = KeposColors.Green600,
            )
            Spacer(modifier = Modifier.weight(1f)) // espaço flexível entre texto e ícone
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Configurações",
                tint = KeposColors.Green500,
                modifier = Modifier
                    .size(36.dp) // tamanho maior para o ícone
                    .clickable {
                        // TODO: implementar ação do clique no ícone de configurações
                    }
            )
        }

        Divider(
            color = KeposColors.Green500,
            thickness = 3.dp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp)) // um espaçamento para separar do grid

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 180.dp),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(6) { index ->
                DendroBox(dendroName = "Déndro 0${index + 1}", "")
            }
            item {
                AddDendroBox()
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = {
                    // TODO: Implementar função de atualizar
                }
            ) {
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

            IconButton(
                onClick = {
                    // TODO: Implementar logout real
                    // navController.navigate("login") {
                    //     popUpTo(0)
                    // }
                }
            ) {
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

package org.example.kepos

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.res.painterResource


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Kepos Desktop",
        state = rememberWindowState(size = DpSize(900.dp, 700.dp)),
        resizable = false,
        icon = painterResource("icon.png")
    ) {
        App()
    }
}
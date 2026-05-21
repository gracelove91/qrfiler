import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.App

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "qrfiler") {
        MaterialTheme(colors = darkColors()) {
            App(window = window)
        }
    }
}

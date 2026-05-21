import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.App

private val QrfilerColors = darkColors(
    primary = Color(0xFF4FC3F7),
    primaryVariant = Color(0xFF0288D1),
    secondary = Color(0xFF81C784),
    background = Color(0xFF121212),
    surface = Color(0xFF1E2A3A),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    error = Color(0xFFC62828)
)

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "qrfiler") {
        MaterialTheme(colors = QrfilerColors) {
            App(window = window)
        }
    }
}

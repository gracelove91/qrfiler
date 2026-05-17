import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "qrfiler") {
        MaterialTheme {
            App()
        }
    }
}

@Composable
@Preview
fun App() {
    var filePath by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("qrfiler", style = MaterialTheme.typography.h3)
        Spacer(modifier = Modifier.height(16.dp))
        Text("파일이나 폴더를 드래그하거나 버튼을 클릭하세요", style = MaterialTheme.typography.body1)
        
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            // TODO: 파일 선택 다이얼로그
        }) {
            Text("파일 선택")
        }
        
        filePath?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text("선택됨: $it", style = MaterialTheme.typography.body2)
        }
    }
}

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetDropEvent
import java.io.File
import javax.swing.JFileChooser

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "qrfiler") {
        MaterialTheme {
            App(window = window)
        }
    }
}

@Composable
@Preview
fun App(window: ComposeWindow) {
    var selectedPath by remember { mutableStateOf<String?>(null) }

    // 드래그앤드롭 (ComposeWindow에 Swing DropTarget 붙이기)
    LaunchedEffect(Unit) {
        window.contentPane.dropTarget = object : DropTarget() {
            override fun drop(dtde: DropTargetDropEvent) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY)
                val files = dtde.transferable.getTransferData(
                    DataFlavor.javaFileListFlavor
                ) as List<*>
                files.firstOrNull()?.let {
                    selectedPath = (it as File).absolutePath
                }
                dtde.dropComplete(true)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("qrfiler", style = MaterialTheme.typography.h3)
        Spacer(modifier = Modifier.height(8.dp))
        Text("파일이나 폴더를 드래그하거나 버튼을 클릭하세요", style = MaterialTheme.typography.body1)

        // 드래그 영역 시각적 표시
        Box(
            modifier = Modifier.padding(vertical = 24.dp)
                .size(200.dp)
                .border(2.dp, color = Color.Gray, shape = MaterialTheme.shapes.medium)
                .background(Color(0xFF1E2A3A)),
            contentAlignment = Alignment.Center
        ) {
            Text("📁", style = MaterialTheme.typography.h2)
        }

        // 파일 선택 버튼
        Button(onClick = {
            val chooser = JFileChooser().apply {
                fileSelectionMode = JFileChooser.FILES_AND_DIRECTORIES
            }
            if(chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
                selectedPath = chooser.selectedFile.absolutePath
            }
        }) {
            Text("파일/폴더 선택")
        }

        // 선택된 경로 표시
        selectedPath?.let { path ->
            Spacer(modifier = Modifier.height(16.dp))
            Text("선택된 경로: $path", style = MaterialTheme.typography.body2, color = Color(0xFF4FC3F7))
        }
    }
}

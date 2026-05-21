package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sun.net.httpserver.HttpServer
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetDropEvent
import java.io.File
import javax.swing.JFileChooser
import network.startFileServer
import ui.QrCodeImage

@Composable
fun App(window: ComposeWindow) {
    var selectedPath by remember { mutableStateOf<String?>(null) }
    var server by remember { mutableStateOf<HttpServer?>(null) }
    var shareUrl by remember { mutableStateOf("") }
    val isSharing = server != null

    DisposableEffect(selectedPath) {
        onDispose {
            server?.stop(0)
            server = null
            shareUrl = ""
        }
    }

    DisposableEffect(window) {
        val oldDropTarget = window.contentPane.dropTarget
        window.contentPane.dropTarget = object : DropTarget() {
            override fun drop(dtde: DropTargetDropEvent) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY)
                val files = dtde.transferable.getTransferData(DataFlavor.javaFileListFlavor) as List<*>
                files.firstOrNull()?.let { selectedPath = (it as File).absolutePath }
                dtde.dropComplete(true)
            }
        }
        onDispose {
            window.contentPane.dropTarget = oldDropTarget
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text("qrfiler", style = MaterialTheme.typography.h3)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "파일을 드래그하거나 버튼을 클릭하세요",
            style = MaterialTheme.typography.body1
        )

        Box(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .size(200.dp)
                .border(
                    2.dp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.medium
                )
                .background(MaterialTheme.colors.surface),
            contentAlignment = Alignment.Center
        ) {
            Text("📁", style = MaterialTheme.typography.h2)
        }

        Button(onClick = {
            val chooser = JFileChooser().apply {
                fileSelectionMode = JFileChooser.FILES_ONLY
            }
            if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
                selectedPath = chooser.selectedFile.absolutePath
            }
        }) {
            Text("파일 선택")
        }

        selectedPath?.let { path ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "선택된 경로: $path",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (!isSharing) {
                Button(
                    onClick = {
                        val file = File(path)
                        val (s, url) = startFileServer(file)
                        server = s
                        shareUrl = url
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary
                    )
                ) {
                    Text("공유 시작")
                }
            } else {
                Button(
                    onClick = {
                        server?.stop(0)
                        server = null
                        shareUrl = ""
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.error
                    )
                ) {
                    Text("공유 중지")
                }
            }
        }

        if (shareUrl.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "다운로드 URL:",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary
            )
            Text(
                shareUrl,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
            Text(
                "같은 Wi-Fi 내 다른 기기에서 위 URL로 접속하세요",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            QrCodeImage(url = shareUrl, size = 200)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "또는 카메라로 QR 코드를 스캔하세요",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import org.jetbrains.skia.Bitmap

@Composable
fun QrCodeImage(url: String, size: Int = 200) {
    val imageBitmap = remember(url) {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, size, size)

        val bitmap = Bitmap().apply {
            allocN32Pixels(size, size)
            val pixels = IntArray(size * size) { index ->
                val x = index % size
                val y = index / size
                if (bitMatrix.get(x, y)) Color.Black.value.toInt() else Color.White.value.toInt()
            }
            installPixels(pixels)
        }
        bitmap.asComposeImageBitmap()
    }

    Image(
        bitmap = imageBitmap,
        contentDescription = "QR Code for download URL",
        modifier = Modifier.size(size.dp)
    )
}

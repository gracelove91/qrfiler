package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import org.jetbrains.skia.Image as SkiaImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@Composable
fun QrCodeImage(url: String, size: Int = 200) {
    val imageBitmap = remember(url) {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, size, size)
        val bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix)

        val stream = ByteArrayOutputStream()
        ImageIO.write(bufferedImage, "PNG", stream)
        val pngBytes = stream.toByteArray()

        SkiaImage.makeFromEncoded(pngBytes).asImageBitmap()
    }

    Image(
        bitmap = imageBitmap,
        contentDescription = "QR Code for download URL",
        modifier = Modifier.size(size.dp)
    )
}

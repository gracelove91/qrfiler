package network

import com.sun.net.httpserver.HttpServer
import java.io.File
import java.net.InetSocketAddress
import java.nio.file.Files
import java.security.SecureRandom
import java.util.concurrent.Executors

private fun generateToken(length: Int = 16): String {
    val random = SecureRandom()
    val chars = "0123456789abcdef"
    return (1..length).map { chars[random.nextInt(chars.length)] }.joinToString("")
}

fun startFileServer(file: File, port: Int = 0): Pair<HttpServer, String> {
    require(file.isFile) { "Only regular files are supported, not directories" }
    val token = generateToken()
    val server = HttpServer.create(InetSocketAddress(port), 0)

    server.createContext("/$token") { exchange ->
        val headers = exchange.responseHeaders
        val mime = Files.probeContentType(file.toPath()) ?: "application/octet-stream"
        headers.add("Content-Type", mime)

        val safeName = file.name.replace("\\", "").replace("\"", "")
        headers.add("Content-Disposition", "attachment; filename=\"$safeName\"")

        exchange.sendResponseHeaders(200, file.length())

        try {
            file.inputStream().use { input ->
                exchange.responseBody.use { output ->
                    input.copyTo(output)
                }
            }
        } catch (e: Exception) {
            exchange.sendResponseHeaders(500, -1)
        } finally {
            exchange.close()
        }
    }

    server.executor = Executors.newFixedThreadPool(4)
    server.start()

    val actualPort = server.address.port
    val ip = getLocalIpAddress()
    return server to "http://$ip:$actualPort/$token"
}

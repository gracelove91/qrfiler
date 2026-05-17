package network

import com.sun.net.httpserver.HttpServer
import java.io.File
import java.net.InetSocketAddress

private fun generateToken(length: Int = 8) : String =
    (1..length).map { "0123456789abcdef".random() }.joinToString("")

fun startFileServer(file: File, port: Int = 0): Pair<HttpServer, String> {
    val token = generateToken()
    val server = HttpServer.create(InetSocketAddress(port), 0)

    server.createContext("/$token") { exchange ->
        val headers = exchange.responseHeaders
        headers.add("Content-Type", "application/octet-stream")
        headers.add("Content-Disposition", "attachment; filename=\"${file.name}\"")

        exchange.sendResponseHeaders(200, file.length())

        file.inputStream().use { input ->
            exchange.responseBody.use { output ->
                input.copyTo(output)
            }
        }
        exchange.close()
    }

    server.executor = null
    server.start()

    val actualPort = server.address.port
    val ip = getLocalIpAddress()
    return server to "http://$ip:$actualPort/$token"
}

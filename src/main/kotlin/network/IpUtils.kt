package network

import java.net.InetAddress
import java.net.NetworkInterface

fun getLocalIpAddress(): String {
    val interfaces = NetworkInterface.getNetworkInterfaces().toList()
    for (iface in interfaces) {
        if (!iface.isUp || iface.isLoopback || iface.isVirtual) {
            continue
        }

        val displayName = iface.displayName.lowercase()
        if (displayName.contains("vpn")) continue

        val name = iface.name.lowercase()

        if (name.contains("en") || name.contains("wi-fi") || name.contains("wlan")) {
            for (addr in iface.inetAddresses.toList()) {
                if (!addr.isLoopbackAddress && addr is InetAddress) {
                    val host = addr.hostAddress
                    if (host.contains(".")) {
                        return host // IPv4
                    }
                }
            }
        }
    }
    return "127.0.0.1"
}
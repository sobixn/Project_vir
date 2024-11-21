package io.github.sobixn.project_vir.listener

import io.github.sobixn.project_vir.api.PVAPI
import io.github.sobixn.project_vir.api.Socket
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import kotlinx.serialization.decodeFromString

/**
 * Listens for incoming donation messages through a WebSocket connection.
 *
 * @property apiKey An API key used for authentication.
 */
class DonationListener(private val apiKey: String) {

    /**
     * Instance of PVAPI used to interact with the SSAPI service.
     */
    private val api = PVAPI()
    /**
     * A `Socket` instance used for handling WebSocket communication within the `DonationListener` class.
     * It is responsible for establishing and managing WebSocket connections to receive messages,
     * such as donation notifications.
     *
     * The `socket` is utilized in the `startWebSocket` function to connect to the specified WebSocket URL
     * and process incoming messages.
     */
    private val socket = Socket()
    /**
     * A reference to the JavaPlugin instance for the DonationListener class.
     * This plugin instance is required to schedule tasks and interact with the Minecraft server.
     */
    private val plugin: JavaPlugin = JavaPlugin.getProvidingPlugin(DonationListener::class.java)

    /**
     * Starts a WebSocket connection to the specified URL and processes incoming messages.
     *
     * @param url The WebSocket URL to connect to.
     */
    fun startWebSocket(url: String) {
        GlobalScope.launch {
            socket.connect(url) { message ->
                Bukkit.getScheduler().runTask(plugin, Runnable {
                    processMessage(message)
                })
            }
        }
    }

    /**
     * Processes a donation message, parses the donation data, and broadcasts a formatted message.
     *
     * @param message The donation message received, in JSON format.
     */
    private fun processMessage(message: String) {
      val donation = parseDonationData(message)
        val streamer = donation?.streamer ?: "알 수 없음"
        val amount = donation?.amount ?: 0

        Bukkit.broadcastMessage("§a[후원 알림] §b$streamer §a님이 §b$amount 원 §a후원을 받았습니다!")
    }
}


/**
 * Parses a JSON response string into a DonationDetails object.
 *
 * @param jsonResponse The JSON response string to be parsed.
 * @return A DonationDetails object containing the parsed data.
 */
// DonationDetails는 미리 정의된 데이터 클래스
fun parseDonationData(jsonResponse: String): PVAPI.DonationDetails {
    return Json.decodeFromString<PVAPI.DonationDetails>(jsonResponse)
}

/**
 * Processes a given donation message and broadcasts the donation details to all players.
 *
 * @param message The donation message in JSON format.
 */
private fun processMessage(message: String) {
    val donation = parseDonationData(message)
    if (donation != null) {
        val streamer = donation.streamer ?: "알 수 없음"
        val amount = donation.amount ?: 0

        Bukkit.broadcastMessage("§a[후원 알림] §b$streamer §a님이 §b$amount 원 §a후원을 받았습니다!")
    } else {
        Bukkit.broadcastMessage("§a[후원 알림] §b알 수 없음 §a님이 §b0 원 §a후원을 받았습니다!")
    }



}

    /**
     * JSON 데이터 파싱
     */


//    private fun parseDonationData(data: String): fechDonationDetails? {
//        return try {
//            Json.decodeFromString(DonationDetails.serializer(), data)
//        } catch (e: Exception) {
//            println("JSON 파싱 오류: ${e.message}")
//            null

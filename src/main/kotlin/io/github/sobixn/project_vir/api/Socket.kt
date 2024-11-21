package io.github.sobixn.project_vir.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*

/**
 * This class provides WebSocket-based communication functionality.
 * It allows you to connect to a WebSocket server and send or receive messages.
 */
class Socket {

    /**
     * HTTP client instance configured with CIO engine and WebSocket plugin.
     * This client is used to establish WebSocket connections for real-time communication.
     */
    private val client: HttpClient = HttpClient(CIO) {
        install(WebSockets) // WebSocket 플러그인
    }

    /**
     * Establishes a WebSocket connection to the specified URL and listens for incoming messages.
     * For each incoming text frame, the provided onMessage callback will be invoked with the message.
     *
     * @param url The WebSocket URL to connect to.
     * @param onMessage A suspend function that will be called with the incoming message as a string.
     */
    suspend fun connect(url: String, onMessage: suspend (String) -> Unit) {
        client.webSocket(urlString = url) {
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    onMessage(frame.readText()) // 메시지 콜백 호출
                }
            }
        }
    }

    /**
     * Sends a WebSocket message to the specified URL.
     *
     * @param url The WebSocket URL to send the message to.
     * @param message The message to send over the WebSocket connection.
     */
    suspend fun sendMessage(url: String, message: String) {
        client.webSocket(urlString = url) {
            send(Frame.Text(message)) // 텍스트 프레임 전송
        }
    }
}

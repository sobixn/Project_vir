package io.github.sobixn.project_vir.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * This class provides methods to interact with the SSAPI.
 * It includes functionality to create an HTTP client and retrieve donation details.
 */
class PVAPI {

    /**
     * Base URL for the SSAPI service.
     * This URL is used as the endpoint for the API requests.
     */
    private val SSAPI_URL = "https://api.ssapi.kr"
    /**
     * An API key used for authenticating requests to the SSAPI service.
     * This key is required for accessing various endpoints provided by SSAPI.
     */
    private val SSAPI_API = "p2hv5ux181x322myad6d6sqt9y45v9ocmr2os3plr69j0ingwb53qpavh8mah7c1"


    /**
     * An instance of `HttpClient` configured with the CIO engine and
     * `ContentNegotiation` plugin for handling JSON content.
     * This client is used to make HTTP requests to the SSAPI service.
     */
// HTTP Client 생성
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    /**
     * Fetches donation details from the server.
     *
     * @return A string containing the donation details fetched from the server.
     */
// 후원 정보를 가져오는 함수 예시
    suspend fun getDonationDetails(): String {
        return withContext(Dispatchers.IO) {
            val response: HttpResponse = client.get("$SSAPI_URL/donation/details") {
                header("Authorization", "Bearer $SSAPI_API")
            }
            response.body<String>()
        }
    }
    @Serializable
    data class DonationDetails(
        val streamer: String,
        val amount: Int,
        val message: String
    )


    /**
     * Companion object for PVAPI class.
     * This can be used to hold static members or methods related to PVAPI.
     */
    companion object {
        // companion object 블록이 필요한 경우 사용
    }


}
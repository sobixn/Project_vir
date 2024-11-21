package io.github.sobixn.project_vir.db

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import java.io.File

/**
 * DonationSystemConfig is an object that handles the configuration for a donation system.
 * It reads from and writes to a JSON file to manage donation data.
 */
object DonationSystemConfig {
    /**
     * Represents the configuration file used for storing donation settings
     * in JSON format.
     *
     * The file is expected to be named "donation_config.json" and is used
     * to persist donation-related data. It is read from and written to
     * during the application's lifecycle.
     */
    private val configFile = File("donation_config.json")

    /**
     * A mutable map that stores donation data where the key is the donation ID and the value is the Mojang ID.
     *
     * This map is used to keep track of donations by associating each donation with a specific Mojang account.
     */
    private var donationData: MutableMap<String, String> = mutableMapOf()

    init {
        // JSON 파일을 읽어 초기화
        if (configFile.exists()) {
            val content = configFile.readText()
            val json = Json.parseToJsonElement(content) as JsonObject
            donationData = Json.decodeFromJsonElement<Map<String, String>>(json).toMutableMap()

        }
    }

    /**
     * Adds a donation to the donation data and saves the configuration.
     *
     * @param id The unique identifier for the donation.
     * @param mojangId The Mojang ID associated with the donation.
     */
    fun addDonation(id: String, mojangId: String) {
        donationData[id] = mojangId
        saveConfig()
    }

    /**
     * Retrieves a map of donations with donor names and their respective donations.
     *
     * @return a map where the key is the donor name and the value is the donation amount.
     */
    fun getDonations(): Map<String, String> = donationData

    /**
     * Saves the current state of the donation data to a configuration file.
     *
     * This method serializes the `donationData` to JSON format and writes it
     * to the specified `configFile`. It ensures that the latest donation information
     * is persisted for future use.
     */
    private fun saveConfig() {
        val json = Json.encodeToJsonElement(donationData)
        configFile.writeText(Json.encodeToString(JsonObject.serializer(), json as JsonObject))
    }
}

package io.github.sobixn.project_vir

import io.github.sobixn.project_vir.command.DonationRegisterCommand
import kr.ssapi.minecraft.DataBase.DonationData
import kr.ssapi.minecraft.SSAPI.Soket
import org.bukkit.plugin.java.JavaPlugin


/**
 * ProjectVirPlugin is a class that extends JavaPlugin and is responsible for
 * managing the lifecycle of a Minecraft plugin. This class performs initialization
 * and cleanup of the plugin when the server starts or stops.
 */
class ProjectVirPlugin : JavaPlugin() {
private lateinit var soket: Soket
private lateinit var donationData:DonationData




    /**
     * This method is called when the plugin is enabled. It registers the "dontation" command
     * and its tab completer using the `DonationRegisterCommand` class.
     *
     * The "dontation" command allows players to register their IDs and test donations in-game.
     * The `DonationRegisterCommand` class handles the command execution and tab completion.
     */
    override fun onEnable() {
        soket.SoketConnection()
        donationData.DataLoad()
        getCommand("dontation")?.setExecutor(DonationRegisterCommand())
        getCommand("dontation")?.setTabCompleter(DonationRegisterCommand())
        // Plugin startup logic
    }

    /**
     * This method is called when the plugin is disabled.
     * Use this method to handle any necessary cleanup or save operations
     * before the plugin is completely shut down.
     */
    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun getINSTANCE(): ProjectVirPlugin {
        return this
    }
}

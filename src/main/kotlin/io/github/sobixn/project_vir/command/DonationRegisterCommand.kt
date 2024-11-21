package io.github.sobixn.project_vir.command

import io.github.sobixn.project_vir.ProjectVirPlugin
import io.github.sobixn.project_vir.api.PVAPI
import io.github.sobixn.project_vir.api.Socket
import kotlinx.coroutines.runBlocking
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

/**
 * Instance of PVAPI used to interact with the SSAPI service.
 * The `PVAPI` class provides functionalities such as creating an HTTP client and retrieving donation details.
 */
private val api = PVAPI()

/**
 * A private instance of the `Socket` class, which manages WebSocket-based communication.
 *
 * This variable is used to establish and manage WebSocket connections, allowing the application
 * to send and receive messages in real-time.
 */
private val socket = Socket()

private val main = ProjectVirPlugin().getINSTANCE()

/**
 * DonationRegisterCommand handles the registration and testing of donations
 * through Minecraft in-game commands.
 */
class DonationRegisterCommand : CommandExecutor, TabCompleter {



    /**
     * Handles the execution of commands sent by CommandSenders.
     *
     * @param sender The source of the command, typically a player.
     * @param command The command that was executed.
     * @param label The command alias that was used.
     * @param args An optional array of arguments passed with the command.
     * @return Boolean indicating whether the command was successfully executed.
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.")
            return true
        }

        val player = sender

        if (args == null || args.isEmpty()) {
            player.sendMessage("명령어 형식이 잘못되었습니다. /후원등록 <ID>")
            return true
        }

        val subCommand = args[0]
        when (subCommand) {
            "dontation" -> {
                if (args.size < 2) {
                    player.sendMessage("명령어 형식이 잘못되었습니다. /후원등록 <ID>")
                    return true
                }
                val id = args[1]
                // 스트리머 ID 등록 로직 추가
                player.sendMessage("ID: $id 님이 등록되었습니다.")
            }
            "dontationtest" -> {
                // 후원 테스트 로직 추가
                player.server.broadcastMessage("${player.name}님이 1000원 후원을 받았습니다!")
                fetchDonationDetails()
            }
            else -> {
                player.sendMessage("알 수 없는 명령어입니다.")
            }
        }

        return true
    }

    /**
     * Fetches and prints donation details from the API.
     *
     * This function is executed within a `runBlocking` block,
     * which means it will block the current thread until the coroutine completes.
     *
     * The donation details are fetched by calling `api.getDonationDetails()`.
     * If the API call is successful, the donation details are printed to the console.
     * If an exception occurs during the API call, the stack trace is printed.
     */
    fun fetchDonationDetails() {
        runBlocking {
            try {
                val donationDetails = api.getDonationDetails()
                println("Donation Details: $donationDetails")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Provides tab completion suggestions for the donation commands.
     *
     * @param sender The sender of the command.
     * @param command The command that was executed.
     * @param label The alias or label of the command.
     * @param args The arguments provided with the command.
     * @return A mutable list of suggestions for tab completion.
     */
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String>? {
        if (args == null || args.isEmpty()) {
            return mutableListOf("dontation", "dontationtest")
        }
        return when (args[0]) {
            "dontation" -> mutableListOf("<ID>")
            else -> mutableListOf()
        }
    }
}

package me.sharp.novaCore.commands;

import me.sharp.novaCore.NovaManager;
import me.sharp.novaCore.novas.Nova;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ListNovasCommand implements CommandExecutor {

    private final NovaManager nova_manager;
    public ListNovasCommand(NovaManager nova_manager) {
        this.nova_manager = nova_manager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return true;
        }

        Collection<Nova> novas = nova_manager.getAllNovas();

        if (novas.isEmpty()) {
            player.sendMessage(ChatColor.RED + "No novas are registered. Check with an admin.");
            return true;
        }

        String nova_list = novas.stream()
                .map(nova -> ChatColor.AQUA + nova.name)
                .collect(java.util.stream.Collectors.joining(ChatColor.GRAY + ", "));

        player.sendMessage("Available Novas: " + nova_list);

        return true;
    }
}

package me.sharp.novaCore.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class GiveNovaCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    public GiveNovaCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /GiveNova <nova_type>");
            return true;
        }

        String nova_type = args[0];

        ItemStack nova = new ItemStack(Material.PAPER);
        ItemMeta nova_meta = nova.getItemMeta();
        if (nova_meta != null) {
            nova_meta.setDisplayName(ChatColor.LIGHT_PURPLE + nova_type);

            NamespacedKey key = new NamespacedKey(plugin, "nova_type");

            nova_meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, nova_type);

            nova.setItemMeta(nova_meta);
        }

        player.getInventory().addItem(nova);
        player.sendMessage("take a nova");

        return true;
    }
}

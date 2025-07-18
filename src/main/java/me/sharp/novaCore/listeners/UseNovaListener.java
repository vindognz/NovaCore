package me.sharp.novaCore.listeners;

import me.sharp.novaCore.NovaCore;
import me.sharp.novaCore.NovaManager;
import me.sharp.novaCore.novas.Nova;
import me.sharp.novaCore.novas.StrengthNova;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class UseNovaListener implements Listener {

    private final JavaPlugin plugin;
    private final NovaManager nova_manager;

    public UseNovaListener(JavaPlugin plugin, NovaManager novaManager) {
        this.plugin = plugin;
        nova_manager = novaManager;
    }

    @EventHandler
    public void onPlayerUseNova(@NotNull PlayerInteractEvent e) {
        switch (e.getAction()) {
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                break;
            default:
                return;
        }

        ItemStack item = e.getItem();
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, "nova_type");

        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;

        String nova_name = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (nova_name == null) return;

        Optional<Nova> optionalNova = nova_manager.getAllNovas().stream()
                .filter(nova -> nova.name.equalsIgnoreCase(nova_name))
                .findFirst();

        Player player = e.getPlayer();

        if (optionalNova.isEmpty()) {
            player.sendMessage(ChatColor.RED + "This Nova no longer exists");
            return;
        }

        Nova nova = optionalNova.get();
        nova.use(player);
    }
}

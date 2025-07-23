package me.sharp.novaCore.novas;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Nova {
    protected final JavaPlugin plugin;
    public final String name;
    protected final long cooldown;
    protected final Map<UUID, Long> lastUse = new HashMap<>();

    public Nova(JavaPlugin plugin, String name, long cooldown) {
        this.plugin = plugin;
        this.name = name;
        this.cooldown = cooldown;
    }

    public boolean canUse(Player player) {
        long now = System.currentTimeMillis();
        return !lastUse.containsKey(player.getUniqueId()) || (now - lastUse.get(player.getUniqueId())) >= cooldown;
    }

    public void use(Player player) {
        if (canUse(player)) {
            lastUse.put(player.getUniqueId(), System.currentTimeMillis());
            activate(player);
        } else {
            long timeLeft = 1 + (cooldown - (System.currentTimeMillis() - lastUse.get(player.getUniqueId()))) / 1000;
            player.sendMessage(ChatColor.RED + "Your Nova is on cooldown for " + timeLeft + " more seconds.");
        }
    }

    public abstract void activate(Player player);
}

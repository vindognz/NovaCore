package me.sharp.novaCore.novas;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GhostNova extends Nova {

    private final FileConfiguration config;

    public GhostNova(JavaPlugin plugin, FileConfiguration config) {
        super(plugin, "Ghost", 180000);
        this.config = config;
    }

    @Override
    public void activate(Player player) {
        int duration = config.getInt("novas.ghost.duration");
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * duration, 1));
    }
}

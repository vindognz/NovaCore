package me.sharp.novaCore.novas;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class SpeedNova extends Nova {

    private final FileConfiguration config;

    public SpeedNova(JavaPlugin plugin, FileConfiguration config) {
        super(plugin, "Speed", 3000); // make this 30 seconds
        this.config = config;
    }

    @Override
    public void activate(Player player) {
        // launch you in the direction you're looking.

        double vertical_clamp = config.getDouble("novas.speed.vertical_clamp");

        Vector playerDirection = player.getLocation().getDirection();
        double speedMultiplier = config.getDouble("novas.speed.speed_multiplier");

        Vector velocity = playerDirection.multiply(speedMultiplier);
        velocity.setY(Math.min(velocity.getY() + 0.5, vertical_clamp));

        player.setVelocity(velocity);
    }
}

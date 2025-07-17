package me.sharp.novaCore.novas;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class StrengthNova extends Nova {

    private final JavaPlugin plugin;

    public StrengthNova(JavaPlugin plugin) {
        super(plugin, "Strength", 180);
        this.plugin = plugin;
    }

    public void ParticleCircle(Player player, Particle type, double rad, int particle_count, int rings) {
        Location base = player.getLocation();
        double between_rings = rad / rings;

        for (int ring_index = 0; ring_index < rings; ring_index++) {
            double ring_radius = between_rings * (ring_index + 1);
            int delay_ticks = ring_index * 3;

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (int particle_index = 0; particle_index <= particle_count; particle_index++) {
                        double angle = (2 * Math.PI / particle_count) * particle_index;
                        Location location = base.clone().add(ring_radius * Math.cos(angle), 0, ring_radius * Math.sin(angle));
                        player.getWorld().spawnParticle(type, location, 1, 0, 0, 0, 0);
                    }
                }
            }.runTaskLater(plugin, delay_ticks);
        }
    }

    @Override
    public void activate(Player player) {
        player.sendMessage("used strength nova");
        ParticleCircle(player,Particle.WHITE_SMOKE,2.5,16, 3);
    }
}

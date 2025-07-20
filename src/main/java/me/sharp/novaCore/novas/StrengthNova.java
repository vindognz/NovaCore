package me.sharp.novaCore.novas;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class StrengthNova extends Nova {

    private final JavaPlugin plugin;

    public StrengthNova(JavaPlugin plugin) {
        super(plugin, "Strength", 5000);
        this.plugin = plugin;
    }

    public void ParticleCircle(Player player, Particle type, double rad, int particle_count, int rings, BlockData extra) {
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
                        Location location = base.clone().add(ring_radius * Math.cos(angle) + Math.random() * 0.5, 0.5 * ring_radius, ring_radius * Math.sin(angle) + Math.random() * 0.5);
                        player.getWorld().spawnParticle(type, location, 1, 0, 0, 0, extra);
                    }
                }
            }.runTaskLater(plugin, delay_ticks);
        }
    }

    @Override
    public void activate(Player player) {
        player.sendMessage("used strength nova");
        player.setVelocity(new Vector(0, 1.5, 0));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnGround()) {

                    List<Player> nearbyPlayers = player.getWorld().getPlayers().stream()
                            .filter(p -> !p.equals(player))
                            .filter(p -> p.getLocation().distance(player.getLocation()) <= 3.5)
                            .toList();

                    Block stood = player.getLocation().add(0, -1, 0).getBlock();
                    ParticleCircle(player,Particle.BLOCK,3.5,32, 4, stood.getType().createBlockData());

                    for (Player target : nearbyPlayers) {
                        Location distance = player.getLocation().subtract(target.getLocation());
                        target.setVelocity(new Vector(-1 / (distance.getX() * 10+0.0001), 0.8, -1 / (distance.getZ() * 10+0.0001)));
                    }

                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 5L, 2L);
    }
}

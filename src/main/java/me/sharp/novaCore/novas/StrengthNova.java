package me.sharp.novaCore.novas;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class StrengthNova extends Nova {

    private final JavaPlugin plugin;
    private final FileConfiguration config;

    public StrengthNova(JavaPlugin plugin, FileConfiguration config) {
        super(plugin, "Strength", 5000); // make this 3m
        this.plugin = plugin;
        this.config = config;
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

    public Vector oldVelo;

    @Override
    public void activate(Player player) {
        player.sendMessage("used strength nova");
        List<Double> velocity = config.getDoubleList("novas.strength.launch_velocity");
        player.setVelocity(new Vector(velocity.get(0), velocity.get(1), velocity.get(2)));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnGround()) {

                    Vector playerVelo = player.getVelocity();

                    double radius = config.getDouble("novas.strength.radius");
                    int particles = config.getInt("novas.strength.particles");
                    int rings = config.getInt("novas.strength.rings");

//                    List<Player> nearbyPlayers = player.getWorld().getPlayers().stream()
//                            .filter(p -> !p.equals(player))
//                            .filter(p -> p.getLocation().distance(player.getLocation()) <= radius)
//                            .toList();

                    List<Entity> nearbyEntities = player.getWorld().getEntities().stream()
                            .filter(p -> !p.equals(player))
                            .filter(p -> p.getLocation().distance(player.getLocation()) <= radius)
                            .toList();

                    Block stood = player.getLocation().add(0, -1, 0).getBlock();

                    ParticleCircle(player,Particle.BLOCK,radius,particles, rings, stood.getType().createBlockData());

//                    for (Player target : nearbyPlayers) {
                    for (Entity target : nearbyEntities) {
//                        double maxDamage = 12; // pull from config file
//                        double minDamage = 5;
//                        double shapeFactor = (maxDamage - minDamage) / (Math.pow(radius, 2));
//                        double dist = Math.abs(target.getLocation().distance(player.getLocation()));
//                        double damage = shapeFactor * Math.pow((dist - radius), 2) + minDamage;
//
//                        target.damage(damage);

                        Vector direction = target.getLocation().toVector().subtract(player.getLocation().toVector());

                        double yaw = Math.atan2(-direction.getX(), direction.getZ());
                        yaw = (yaw + (2 * Math.PI)) % (2 * Math.PI);

                        double dist = target.getLocation().distance(player.getLocation());

                        double speedMultiplier = 2;

                        double x = (-Math.sin(yaw) * 1/dist) * speedMultiplier;
                        x = Math.min(x, 2);
                        double z = (Math.cos(yaw) * 1/dist) * speedMultiplier;
                        z = Math.min(z, 2);

                        Vector speed = new Vector(x, 0.8, z);

                        target.setVelocity(speed);
                    }

                    this.cancel();
                } else {
                    oldVelo = player.getVelocity();
                }
            }
        }.runTaskTimer(plugin, 4L, 2L);
    }
}

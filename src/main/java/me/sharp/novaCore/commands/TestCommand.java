package me.sharp.novaCore.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TestCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public  TestCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void ParticleCircle(Player player, Particle type, double rad, int particle_count, int rings) {
        Location base = player.getLocation();
        double betweenRings = rad / rings;

        for (int ring_index = 0; ring_index < rings; ring_index++) {
            double ring_radius = betweenRings * (ring_index + 1); // Still avoid zero-radius
            int delay_ticks = ring_index * 3; // First ring: 0 ticks, next: 10, 20, ...

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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can run this command!");
            return false;
        }

        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("test")) {
            player.sendMessage("Test!");
            ParticleCircle(player,Particle.WHITE_SMOKE,2.5,16, 3);
        }
        return true;
    }
}

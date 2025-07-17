package me.sharp.novaCore.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {

    public void ParticleCircle(Player player, Particle type, Double rad, Integer particle_count) {
        Location base = player.getLocation();
        for (int i = 0; i <= particle_count; i++) {
            Double angle = (2*Math.PI / particle_count) * i;
            Location location = base.clone().add(rad*Math.cos(angle), 0, rad*Math.sin(angle));
            player.getWorld().spawnParticle(type,location,1, 0, 0, 0, 0);
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
            ParticleCircle(player,Particle.WHITE_SMOKE,2.5,16);
        }
        return true;
    }
}

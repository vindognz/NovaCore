package me.sharp.novaCore;

import me.sharp.novaCore.commands.GiveNovaCommand;
import me.sharp.novaCore.commands.ListNovasCommand;
import me.sharp.novaCore.listeners.UseNovaListener;
import me.sharp.novaCore.novas.GhostNova;
import me.sharp.novaCore.novas.SpeedNova;
import me.sharp.novaCore.novas.StrengthNova;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class NovaCore extends JavaPlugin {

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        FileConfiguration config =  this.getConfig();

        NovaManager nova_manager = new NovaManager();

        nova_manager.registerNova(new StrengthNova(this, config)); // effect mostly done, just needs tweaking
        nova_manager.registerNova(new SpeedNova(this, config)); // dash done. needs tweaking
        // haste nova
        // fire nova
        nova_manager.registerNova(new GhostNova(this, config)); // **should** give you invis
        // blizzard nova
        // thunder nova
        // solflare nova

        Bukkit.getLogger().info("NovaCore enabled!");
        Objects.requireNonNull(this.getCommand("givenova")).setExecutor(new GiveNovaCommand(this, nova_manager));
        Objects.requireNonNull(this.getCommand("listnovas")).setExecutor(new ListNovasCommand(nova_manager));

        getServer().getPluginManager().registerEvents(new UseNovaListener(this, nova_manager), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("NovaCore disabled!");
    }
}

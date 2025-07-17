package me.sharp.novaCore;

import me.sharp.novaCore.commands.TestCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class NovaCore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("NovaCore enabled!");
        getCommand("test").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("NovaCore disabled!");
    }
}

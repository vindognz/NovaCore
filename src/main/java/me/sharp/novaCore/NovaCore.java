package me.sharp.novaCore;

import me.sharp.novaCore.commands.GiveNovaCommand;
import me.sharp.novaCore.commands.TestCommand;
import me.sharp.novaCore.novas.StrengthNova;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class NovaCore extends JavaPlugin {

    @Override
    public void onEnable() {
        NovaManager nova_manager = new NovaManager();

        nova_manager.registerNova(new StrengthNova(this));

        Bukkit.getLogger().info("NovaCore enabled!");
        Objects.requireNonNull(this.getCommand("test")).setExecutor(new TestCommand());
        Objects.requireNonNull(this.getCommand("GiveNova")).setExecutor(new GiveNovaCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("NovaCore disabled!");
    }
}

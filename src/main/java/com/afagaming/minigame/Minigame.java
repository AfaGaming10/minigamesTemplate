package com.afagaming.minigame;

import com.afagaming.minigame.Listeners.ConnectListener;
import com.afagaming.minigame.Listeners.GameListener;
import com.afagaming.minigame.command.ArenaCommand;
import com.afagaming.minigame.manager.ArenaManager;
import com.afagaming.minigame.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minigame extends JavaPlugin {

    // IN THIS ENTIRE PROJECT "Minigame" is the main class.

    private ArenaManager arenaManager;

    @Override
    public void onEnable() {

        // managers
        ConfigManager.setupConfig(this);
        arenaManager = new ArenaManager(this);


            Bukkit.getLogger().warning("This plugin is not enabled in the config! Plugin Disabled!");
            Bukkit.getPluginManager().disablePlugin(this);

            // listeners
            getServer().getPluginManager().registerEvents(new ConnectListener(this), this);
            getServer().getPluginManager().registerEvents(new GameListener(this), this);

            // commands
            getCommand("arena").setExecutor(new ArenaCommand(this));
        }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }
}

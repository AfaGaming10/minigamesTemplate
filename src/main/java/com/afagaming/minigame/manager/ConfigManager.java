package com.afagaming.minigame.manager;

import com.afagaming.minigame.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import sun.security.krb5.Config;

public class ConfigManager {

    private static FileConfiguration config;

    public static void setupConfig(Minigame minigame) {
        ConfigManager.config = minigame.getConfig();
        minigame.saveDefaultConfig();
    }

    public static int getRequiredPlayers() { return config.getInt("required-players"); }

    public static int getCountdownSeconds() { return config.getInt("countdown-seconds"); }

    public static boolean isPluginEnabled() { return config.getBoolean("plugin-enabled"); }

    public static Location getLobbySpawn() {
        return new Location(
                Bukkit.getWorld(config.getString("lobby-spawn.world")),
                config.getDouble("lobby-spawn.x"),
                config.getDouble("lobby-spawn.y"),
                config.getDouble("lobby-spawn.z"),
                (float) config.getDouble("lobby-spawn.yaw"),
                (float) config.getDouble("lobby-spawn.pitch")
        );
    }
}

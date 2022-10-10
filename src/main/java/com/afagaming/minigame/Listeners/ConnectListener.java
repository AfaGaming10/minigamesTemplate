package com.afagaming.minigame.Listeners;

import com.afagaming.minigame.GameState;
import com.afagaming.minigame.Minigame;
import com.afagaming.minigame.instance.Arena;
import com.afagaming.minigame.instance.Game;
import com.afagaming.minigame.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    private Minigame minigame;


    // constructor
    public ConnectListener(Minigame minigame) {
        this.minigame = minigame;
    }



    // join event

    @EventHandler
    public void onConnect(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Location lobbySpawn = ConfigManager.getLobbySpawn();

        player.teleport(lobbySpawn);
        e.setJoinMessage(ChatColor.GREEN + player.getName() + ChatColor.GOLD + " spooked into the lobby!");
        player.setHealth(20);
    }

    // leave event

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Arena arena = minigame.getArenaManager().getArena(player);

        if (arena != null) {
            arena.removePlayer(e.getPlayer());
            }
        }
    }

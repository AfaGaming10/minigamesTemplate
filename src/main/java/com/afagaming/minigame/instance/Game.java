package com.afagaming.minigame.instance;

import com.afagaming.minigame.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Game {

    private Arena arena;
    private HashMap<UUID, Integer> points;

    // constructor
    public Game(Arena arena) {
        this.arena = arena;
        // the this keyword is not required below:
        this.points = new HashMap<>();
    }

    public void start() {
        arena.setState(GameState.LIVE);
        arena.sendMessage(ChatColor.GREEN + "Game has started!" + "Your objective is to break 20 blocks in the fastest time! Good luck!");

        for (UUID uuid : arena.getPlayers()) {
            points.put(uuid, 0);
        }
    }

    // add point method
    public void addPoint(Player player) {
        int PlayerPoints = points.get(player.getUniqueId()) + 1;

        if (PlayerPoints == 20) {

            // celebrating the victory!
            player.sendMessage(ChatColor.GREEN + "You have won! Good job!");
            arena.sendMessage(ChatColor.GOLD + "WE HAVE A WINNER!");
            arena.sendMessage(ChatColor.GOLD + player.getName() + " HAS WON!");
            arena.sendMessage(ChatColor.GREEN + "Thanks for playing!");
            arena.sendMessage(ChatColor.RED + "[ALERT]" + ChatColor.GREEN + "Make sure to say " + ChatColor.GOLD + "GG!");

            // reset arena
            arena.reset(true);

            return;
        } else {
            player.sendMessage(ChatColor.GREEN + "Added 1 Point!");
            points.replace(player.getUniqueId(), PlayerPoints);
        }
    }
}

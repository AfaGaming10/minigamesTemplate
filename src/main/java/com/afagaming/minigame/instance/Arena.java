package com.afagaming.minigame.instance;

import com.afagaming.minigame.GameState;
import com.afagaming.minigame.Minigame;
import com.afagaming.minigame.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Arena {

    private Minigame minigame;

    private int id;
    private Location spawn;

    // trs
    private GameState state;
    private List<UUID> players;
    // kits
    private Countdown countdown;
    private Game game;

    // tre

    public Arena(Minigame minigame, int id, Location spawn) {
        this.minigame = minigame;

        this.id = id;
        this.spawn = spawn;

        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.countdown = new Countdown(minigame, this);
        this.game = new Game(this);
    }

    // GAME

    public void start() { game.start(); }

    public void reset(boolean kickPlayers) {
        // resetting everyting
        // what are we resetting? keyword: "trs" for start of thins and "tre" for end of things

        if (kickPlayers == true) {
            for (UUID uuid : players) {
                Location loc = ConfigManager.getLobbySpawn();
                Player player = Bukkit.getPlayer(uuid);
                player.teleport(loc);
            }
            players.clear();
        }
        sendTitle("", "");
        state = GameState.RECRUITING;
        countdown.cancel();
        countdown = new Countdown(minigame, this);
        Game game = new Game(this);
    }


    // TOOLS

    public void sendMessage(String message) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendTitle(String title, String subtitle) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendTitle(title, subtitle);
        }
    }

    //  MANAGE PLAYERS

    // add player method
    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        player.teleport(spawn);

        player.sendMessage(ChatColor.GOLD + "Choose your kit with /kit.");

        if (state.equals(GameState.RECRUITING) && players.size() >= ConfigManager.getRequiredPlayers()) {
            countdown.start();
        }
    }

    // remove player method

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
        player.sendTitle("", "");

        if (state == GameState.COUNTDOWN && players.size() < ConfigManager.getRequiredPlayers()) {
            reset(false);
            sendMessage(ChatColor.RED + "Minimum player count hasn't been met! Countdown stopped.");
            return;
        }
        if (state == GameState.LIVE && players.size() < ConfigManager.getRequiredPlayers()) {
            reset(false);
            sendMessage(ChatColor.RED + player.getName() + "has disconnected from your game.");
        }
    }

    // INFO
    public int getId() { return id; }

    public GameState getState() { return state; }

    // set state method
    public void setState(GameState state) { this.state = state; }

    public List<UUID> getPlayers() { return players; }
    public Game getGame() { return game; }
}

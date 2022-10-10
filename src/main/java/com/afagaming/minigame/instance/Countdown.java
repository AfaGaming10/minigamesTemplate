package com.afagaming.minigame.instance;

import com.afagaming.minigame.GameState;
import com.afagaming.minigame.Minigame;
import com.afagaming.minigame.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    // constructor

    private Minigame minigame;
    private Arena arena;
    private int countdownSeconds;

    public Countdown(Minigame minigame, Arena arena) {
        this.minigame = minigame;
        this.arena = arena;
        this.countdownSeconds = ConfigManager.getCountdownSeconds();
    }

    public void start() {
        arena.setState(GameState.COUNTDOWN);

        runTaskTimer(minigame, 0, 20);
    }

    @Override
    public void run() {
        if (countdownSeconds == 0) {
            cancel();
            arena.start();
            return;
        }

        if (countdownSeconds <= 5 || countdownSeconds % 10 == 0) {
            arena.sendMessage(ChatColor.GREEN + "Game will start in " + countdownSeconds + " second" +
                    (countdownSeconds == 1 ? "" : "s") + ".");
        }

        arena.sendTitle(ChatColor.GREEN.toString() + countdownSeconds , ChatColor.GRAY + "seconds left");

        countdownSeconds --;
    }
}

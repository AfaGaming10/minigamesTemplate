package com.afagaming.minigame.Listeners;

import com.afagaming.minigame.Minigame;
import com.afagaming.minigame.instance.Arena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GameListener implements Listener {

    private Minigame minigame;

    // constructor
    public GameListener(Minigame minigame) {
        this.minigame = minigame;
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Arena arena = minigame.getArenaManager().getArena(e.getPlayer());

        if (arena != null) {
            switch (arena.getState()) {
                case RECRUITING:
                    e.setCancelled(true);
                    break;
                case COUNTDOWN:
                    e.setCancelled(true);
                case LIVE:
                    arena.getGame().addPoint(e.getPlayer());
                    e.setCancelled(false);
            }
        } else {
            // player is in lobby so cant break blocks
            e.setCancelled(true);
        }
    }

}

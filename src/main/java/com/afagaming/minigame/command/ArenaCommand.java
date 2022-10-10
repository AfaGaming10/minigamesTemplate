package com.afagaming.minigame.command;

import com.afagaming.minigame.GameState;
import com.afagaming.minigame.Minigame;
import com.afagaming.minigame.instance.Arena;
import com.afagaming.minigame.manager.ArenaManager;
import com.afagaming.minigame.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.map.MapRenderer;

public class ArenaCommand implements CommandExecutor {

    private Minigame minigame;

    // constructor
    public ArenaCommand(Minigame minigame) {
        this.minigame = minigame;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // command: /arena <list/join/leave>

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    player.sendMessage(ChatColor.GREEN + "Avalible arenas:");
                    for (Arena arena : minigame.getArenaManager().getArenas()) {
                        player.sendMessage(ChatColor.GREEN + "- " + arena.getId() + ": " + arena.getState().name());
                    }
                } else if (args[0].equalsIgnoreCase("leave")) {
                    Arena arena = minigame.getArenaManager().getArena(player);
                    if (arena != null) {
                        arena.removePlayer(player);
                        player.sendMessage(ChatColor.GREEN + "You left the arena!");
                    } else {
                        player.sendMessage(ChatColor.RED + "You are not in an arena!");
                    }
                } else if (args[0].equalsIgnoreCase("join")) {
                    if (minigame.getArenaManager().getArena(player) != null) {
                        player.sendMessage(ChatColor.RED + "You are already in an arena!");
                        return false;
                    } else {
                        int id;
                        try {
                            id = Integer.parseInt(args[1]);
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            player.sendMessage(ChatColor.RED + "Please provide an ID!");
                            return false;
                        }

                        if (id >= 0 && id < minigame.getArenaManager().getArenas().size()) {
                            Arena arena = minigame.getArenaManager().getArena(id);
                            if (arena.getState() == GameState.RECRUITING || arena.getState() == GameState.COUNTDOWN) {
                                arena.addPlayer(player);
                                player.sendMessage(ChatColor.GREEN + "You joined the arena!");
                                arena.sendMessage(ChatColor.GREEN + player.getName() + ChatColor.YELLOW + "entered the arena!");
                            } else {
                                player.sendMessage(ChatColor.RED + "Unable to join arena!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You specified an invalid ID!");
                            return false;
                        }
                    }
                    // below
                } else if (args[0].equalsIgnoreCase("kit")) {
                    Arena arena = minigame.getArenaManager().getArena(player);

                    if (arena != null) {
                        if (arena.getState() != GameState.LIVE) {
                        } else {
                            player.sendMessage(ChatColor.RED + "You can not select a kit while the game is live!");
                        }
                    } else { player.sendMessage(ChatColor.RED + "You are not in an arena!"); }
                } else if (args[0].equalsIgnoreCase("start")) {
                    if (player.hasPermission("minigame.start")) {
                        Arena arena = minigame.getArenaManager().getArena(player);
                        if (arena.getPlayers().size() < ConfigManager.getRequiredPlayers()) {
                            player.sendMessage(ChatColor.RED + "There are not enough players to start the game!");
                        } else {
                            arena.start();
                            player.sendMessage(ChatColor.GREEN + "Starting Arena");
                        }
                    } else { player.sendMessage(ChatColor.RED + "You don't have permission to do that!"); }
                } else if (args[0].equalsIgnoreCase("forcestart")) {
                    if (player.hasPermission("minigame.forcestart")) {
                        player.sendMessage(ChatColor.GREEN + "Force starting the arena!");
                        try {
                            Arena arena = minigame.getArenaManager().getArena(player);
                            arena.start();
                        } catch (Exception ex) {
                            player.sendMessage(ChatColor.RED + "Error starting the arena!");
                        }
                        }
                } else if (args[0].equalsIgnoreCase("forceend")) {
                    Arena arena = minigame.getArenaManager().getArena(player);
                    arena.sendMessage(ChatColor.RED + "Your game game was forcefully ended by an admin.");
                    arena.reset(true);
                } else if (args[0].equalsIgnoreCase("forcejoin")) {
                    if (args.length == 2) {
                        try {
                            Arena arena = minigame.getArenaManager().getArena(Integer.parseInt(args[1]));

                            arena.addPlayer(player);
                            player.sendMessage(ChatColor.GREEN + "You successfully invaded the arena!");
                            } catch (ArrayIndexOutOfBoundsException | CommandException | NumberFormatException ex) {
                            player.sendMessage(ChatColor.RED + "Invalid arena ID!");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "invalid usage! These are the options:");
                    player.sendMessage(ChatColor.RED + "/arena list - lists all arenas");
                    player.sendMessage(ChatColor.RED + "/arena join <id> - join an arena");
                    player.sendMessage(ChatColor.RED + "/arena leave - leave an arena");
                    player.sendMessage(ChatColor.RED + "/arena kit - select a kit");
                    player.sendMessage(ChatColor.RED + "/arena start - start an arena");
                    player.sendMessage(ChatColor.RED + "/arena forcestart - ignore required player and start an arena");
                    player.sendMessage(ChatColor.RED + "/arena forceend - force ends your current arena.");
                    player.sendMessage(ChatColor.RED + "/arena forcejoin - force join an arena.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "invalid usage! These are the options:");
                player.sendMessage(ChatColor.RED + "/arena list - lists all arenas");
                player.sendMessage(ChatColor.RED + "/arena join <id> - join an arena");
                player.sendMessage(ChatColor.RED + "/arena kit - select a kit");player.sendMessage(ChatColor.RED + "/arena start - start an arena");
                player.sendMessage(ChatColor.RED + "/arena forcestart - ignore required player and start an arena");
                player.sendMessage(ChatColor.RED + "/arena forceend - force ends your current arena.");
                player.sendMessage(ChatColor.RED + "/arena forcejoin - force join an arena.");
            }

            return false;
        } else { sender.sendMessage("You have to be a player to run this command!"); }
    return false;
    }
}

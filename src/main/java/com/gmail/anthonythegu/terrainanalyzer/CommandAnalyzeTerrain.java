package com.gmail.anthonythegu.terrainanalyzer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAnalyzeTerrain implements CommandExecutor {

    private GlobalTerrainScanner globalTerrainScanner;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain (thischunk|loadedchunks) [ignore]");
                return false;
            }

            if (args.length == 1) {
                if (args[0].toLowerCase().equals("thischunk")) { // EX: analyzeterrain thischunk

                    TerrainScanner ts = new TerrainScanner();
                    ts.scanChunk(p.getLocation().getChunk(), "");

                    p.sendMessage(prefix() + " The average height of this chunk is " + ts.average() + " blocks");
                    p.sendMessage(prefix() + " The mode height of this chunk is " + ts.mode() + " blocks");
                    return true;

                } else if (args[0].toLowerCase().equals("loadedchunks")) { // EX: analyzeterrain loadedchunks

                    TerrainScanner ts = new TerrainScanner();
                    ts.scanLoadedChunks(p.getWorld(), "");

                    p.sendMessage(prefix() + " The average height of all loaded chunks in this world is " + ts.average()
                            + " blocks");
                    p.sendMessage(prefix() + " The mode height of all loaded chunks in this world is " + ts.mode()
                            + " blocks");
                    return true;

                } else if (args[0].toLowerCase().equals("global")) { // EX: analyzeterrain global

                    p.sendMessage(globalTerrainScanner.showStatistics());
                    return true;

                } else { // EX: analyzeterrain blah
                    sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain (thischunk|loadedchunks) [ignore]");
                    return false;
                }
            }

            if (args.length == 2) { // EX: analyzeterrain thischunk ignore
                sender.sendMessage(
                        ChatColor.RED + "Usage: /analyzeterrain (thischunk|loadedchunks) [ignore] [<blocks>]");
                return false;
            }

            if (args.length == 3) {
                if (args[0].toLowerCase().equals("thischunk") && args[1].toLowerCase().equals("ignore")) {

                    TerrainScanner ts = new TerrainScanner();
                    ts.scanChunk(p.getLocation().getChunk(), args[2]);

                    p.sendMessage(prefix() + " The average height of this chunk is " + ts.average() + " blocks");
                    p.sendMessage(prefix() + " The mode height of this chunk is " + ts.mode() + " blocks");
                    return true;

                } else if (args[0].toLowerCase().equals("loadedchunks") && args[1].toLowerCase().equals("ignore")) {

                    TerrainScanner ts = new TerrainScanner();
                    ts.scanLoadedChunks(p.getWorld(), args[2]);

                    p.sendMessage(
                            prefix() + " The average height of all loaded chunks in this world is " + ts.average()
                                    + " blocks");
                    p.sendMessage(prefix() + " The mode height of all loaded chunks in this world is " + ts.mode()
                            + " blocks");
                    return true;

                } else { // EX: analyzeterrain blah ignore stone
                    sender.sendMessage(
                            ChatColor.RED + "Usage: /analyzeterrain (thischunk|loadedchunks) ignore <blocks>");
                    return false;
                }
            }

            if (args.length >= 4) { // EX: analyzeterrain thischunk ignore stone air water
                sender.sendMessage(
                        ChatColor.RED + "Usage: /analyzeterrain (thischunk|loadedchunks) ignore BLOCK1,BLOCK2...");
                return false;
            }
        }
        return false;

    }

    public void registerScanner(GlobalTerrainScanner scanner) {
        globalTerrainScanner = scanner;
    }

    private static String prefix() {
        return ChatColor.DARK_AQUA + "[TerrainScanner]" + ChatColor.RESET;
    }
}
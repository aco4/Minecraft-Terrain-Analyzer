package com.gmail.anthonythegu.terrainanalyzer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAnalyzeTerrain implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain (chunk|scan) [ignore]");
                return false;
            }

            if (args.length == 1) {
                if (args[0].toLowerCase().equals("chunk")) { // analyzeterrain chunk
                    Statistics s = TerrainScanner.scanChunk(p, "");
                    p.sendMessage(prefix() + " The average height of this chunk is " + s.average() + " blocks");
                    p.sendMessage(prefix() + " The mode height of this chunk is " + s.mode() + " blocks");
                    return true;
                } else if (args[0].toLowerCase().equals("scan")) { // analyzeterrain scan
                    sender.sendMessage(
                            ChatColor.RED + "ERROR: This command is in beta and has not been implemented yet!");
                    return false;
                } else { // analyzeterrain blah
                    sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain (chunk|scan) [ignore]");
                    return false;
                }
            }

            if (args.length == 2) {
                if (args[0].toLowerCase().equals("chunk")) {
                    if (args[1].toLowerCase().equals("ignore")) { // analyzeterrain chunk ignore
                        sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain chunk ignore <blocks>");
                        return false;
                    } else { // analyzeterrain chunk blah
                        sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain chunk [ignore] <blocks>");
                        return false;
                    }
                } else { // analyzeterrain blah blah
                    sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain (chunk|scan) [ignore]");
                    return false;
                }
            }

            if (args.length == 3) {
                if (args[0].toLowerCase().equals("chunk")) {
                    if (args[1].toLowerCase().equals("ignore")) { // analyzeterrain chunk ignore stone
                        Statistics s = TerrainScanner.scanChunk(p, args[2]);
                        p.sendMessage(prefix() + " The average height of this chunk is " + s.average() + " blocks");
                        p.sendMessage(prefix() + " The mode height of this chunk is " + s.mode() + " blocks");
                        return true;
                    } else { // analyzeterrain chunk blah blah
                        sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain chunk [ignore] <blocks>");
                        return false;
                    }
                } else { // analyzeterrain blah blah blah
                    sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain (chunk|scan) [ignore]");
                    return false;
                }
            }

            if (args.length >= 4) {
                if (args[0].toLowerCase().equals("chunk")) {
                    if (args[1].toLowerCase().equals("ignore")) { // analyzeterrain chunk ignore stone water
                        sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain chunk ignore BLOCK1,BLOCK2...");
                        return false;
                    } else { // analyzeterrain chunk blah blah blah
                        sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain chunk [ignore] <blocks>");
                        return false;
                    }
                } else { // analyzeterrain blah blah blah blah
                    sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain (chunk|scan) [ignore]");
                    return false;
                }
            }
        }
        return false;
    }

    private static String prefix() {
        return ChatColor.DARK_AQUA + "[TerrainScanner]" + ChatColor.RESET;
    }
}
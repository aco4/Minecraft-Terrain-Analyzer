package com.gmail.anthonythegu.terrainanalyzer;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAnalyzeTerrain implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain (chunk|scan) [ignore]");
                return false;
            }

            if (args.length == 1) {
                if (args[0].toLowerCase().equals("chunk")) { // analyzeterrain chunk
                    TerrainScanner.scanChunk((Player) sender, new ArrayList<Material>());
                    return false;
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
                        TerrainScanner.scanChunk((Player) sender, toMaterials(args[3]));
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

    private ArrayList<Material> toMaterials(String string) {
        String upper = string.toUpperCase();
        String[] arr = upper.split(",");
        ArrayList<Material> arrList = new ArrayList<>();
        for (String s : arr) {
            Material toAdd = Material.getMaterial(s);
            if (toAdd != null)
                arrList.add(toAdd);
        }
        return arrList;
    }
}
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
                sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain (chunk|startscan|stopscan)");
                return false;
            }

            if (args.length == 1
                    && args[0].toLowerCase().equals("chunk")) {
                sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain chunk (average|mode) [ignore] <blocks>");
                return false;
            }

            if (args.length == 3
                    && args[0].toLowerCase().equals("chunk")
                    && args[1].toLowerCase().equals("average")
                    && args[2].toLowerCase().equals("ignore")) {
                sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain chunk average ignore <blocks>");
                return false;
            }

            if (args.length == 3
                    && args[0].toLowerCase().equals("chunk")
                    && args[1].toLowerCase().equals("mode")
                    && args[2].toLowerCase().equals("ignore")) {
                sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain chunk mode ignore <blocks>");
                return false;
            }

            if (args.length > 4
                    && args[0].toLowerCase().equals("chunk")
                    && args[1].toLowerCase().equals("average")) {
                sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain chunk average ignore <block1,block2>");
                return false;
            }

            if (args.length > 4
                    && args[0].toLowerCase().equals("chunk")
                    && args[1].toLowerCase().equals("mode")) {
                sender.sendMessage(ChatColor.RED + "Usage: /analyzeterrain chunk mode ignore <block1,block2>");
                return false;
            }

            if (args.length == 1 && args[0].toLowerCase().equals("startscan")) {
                sender.sendMessage(ChatColor.RED + "ERROR: This command is in beta and has not been implemented yet!");
                return false;
            }

            if (args.length == 1 && args[0].toLowerCase().equals("stopscan")) {
                sender.sendMessage(ChatColor.RED + "ERROR: This command is in beta and has not been implemented yet!");
                return false;
            }

            if (args.length == 2 && args[0].toLowerCase().equals("chunk") && args[1].toLowerCase().equals("average")) {
                TerrainScanner.scanChunk((Player) sender, new ArrayList<>());
                return true;
            }

            if (args[0].toLowerCase().equals("chunk") && args[1].toLowerCase().equals("average")) {
                TerrainScanner.scanChunk((Player) sender, toMaterials(args[3]));
                return true;
            }
        }
        return false;
    }

    private ArrayList<Material> toMaterials(String string) {
        String upper = string.toUpperCase();
        String[] arr = upper.split(",");
        ArrayList<Material> arrList = new ArrayList<>();
        for (String s : arr) {
            arrList.add(Material.getMaterial(s));
        }
        return arrList;
    }
}
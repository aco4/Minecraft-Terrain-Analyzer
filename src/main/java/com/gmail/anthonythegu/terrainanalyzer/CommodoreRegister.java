package com.gmail.anthonythegu.terrainanalyzer;

import java.io.IOException;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import com.mojang.brigadier.tree.LiteralCommandNode;

import me.lucko.commodore.Commodore;
import me.lucko.commodore.file.CommodoreFileFormat;

public class CommodoreRegister {
    // Commodore https://github.com/lucko/commodore
    // You will need to put this method inside another class to prevent classloading
    // errors when your plugin loads on pre 1.13 versions.
    public static void registerCompletions(Commodore commodore, PluginCommand command, Plugin plugin) {
        LiteralCommandNode<?> analyzeTerrainCommand;
        try {
            analyzeTerrainCommand = CommodoreFileFormat.parse(plugin.getResource("analyzeterrain.commodore"));
            commodore.register(command, analyzeTerrainCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

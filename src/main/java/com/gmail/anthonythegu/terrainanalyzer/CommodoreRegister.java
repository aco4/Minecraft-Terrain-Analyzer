package com.gmail.anthonythegu.terrainanalyzer;

import org.bukkit.command.PluginCommand;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import me.lucko.commodore.Commodore;

public class CommodoreRegister {
    // Commodore https://github.com/lucko/commodore
    // You will need to put this method inside another class to prevent classloading
    // errors when your plugin loads on pre 1.13 versions.
    public static void registerCompletions(Commodore commodore, PluginCommand command) {

        commodore.register(command, LiteralArgumentBuilder.literal("analyzeterrain")
                .then(RequiredArgumentBuilder.argument("chunk", StringArgumentType.string())
                        .then(RequiredArgumentBuilder.argument("height", StringArgumentType.string())
                                .then(RequiredArgumentBuilder.argument("ignore", StringArgumentType.string())
                                        .then(RequiredArgumentBuilder.argument("BLOCK1,BLOCK2...",
                                                StringArgumentType.string())))))
                .then(RequiredArgumentBuilder.argument("scan", StringArgumentType.string())));
    }
}

package com.gmail.anthonythegu.terrainanalyzer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkLoadListener implements Listener {

    private GlobalTerrainScanner globalTerrainScanner;

    @EventHandler
    public void newChunkEvent(ChunkLoadEvent e) {
        globalTerrainScanner.scanChunk(e.getChunk());
    }

    public void registerScanner(GlobalTerrainScanner scanner) {
        this.globalTerrainScanner = scanner;
    }
}
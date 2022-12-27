package com.gmail.anthonythegu.terrainanalyzer;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class TerrainScanner {
    private static final int WORLD_UPPER = 319;
    private static final int WORLD_LOWER = -64;

    // private boolean registerChunk(Chunk chunk) {
    // if (!scannedChunks.contains(chunk)) {
    // scannedChunks.add(chunk);
    // return true;
    // }
    // return false;
    // }

    public static Statistics scanChunk(Chunk chunk, String ignored) {

        Statistics statistics = new Statistics();
        ArrayList<Material> filter = toMaterials(ignored);
        int cx = 16 * chunk.getX();
        int cz = 16 * chunk.getZ();

        for (int x = cx; x < cx + 16; x++) {
            for (int z = cz; z < cz + 16; z++) {
                Location location = new Location(chunk.getWorld(), x, 0, z);

                int highestY = getTerrainHeight(location, filter);
                statistics.add(highestY);
            }
        }
        return statistics;
    }

    public static Statistics scanChunk(Player player, String ignored) {
        return scanChunk(player.getLocation().getChunk(), ignored);
    }

    private static int getTerrainHeight(Location location, ArrayList<Material> ignored) {

        World w = location.getWorld();
        int x = location.getBlockX();
        int z = location.getBlockZ();

        int y;
        for (y = WORLD_UPPER; y >= WORLD_LOWER; y--) {
            Location l = new Location(w, x, y, z);
            Block b = l.getBlock();
            if (!b.isPassable() && !ignored.contains(b.getType()))
                return y;
        }
        return y;
    }

    private static ArrayList<Material> toMaterials(String string) {
        ArrayList<Material> out = new ArrayList<>();
        if (string.length() > 0) {
            for (String block : string.split(",")) {
                Material material = Material.matchMaterial(block);
                if (material != null)
                    out.add(material);
            }
        }
        return out;
    }
}

package com.gmail.anthonythegu.terrainanalyzer;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class TerrainScanner {
    private static final int WORLD_UPPER = 319;
    private static final int WORLD_LOWER = -64;

    private ArrayList<Chunk> scannedChunks;

    private RollingAverage rollingAverage;
    private RollingMode rollingMode;

    public TerrainScanner() {
        scannedChunks = new ArrayList<>();
        rollingAverage = new RollingAverage();
        rollingMode = new RollingMode();
    }

    public void add(int num) {
        rollingAverage.add(num);
        rollingMode.add(num);
    }

    public float average() {
        return rollingAverage.get();
    }

    public int mode() {
        return rollingMode.get();
    }

    private boolean registerChunk(Chunk chunk) {
        if (!scannedChunks.contains(chunk)) {
            scannedChunks.add(chunk);
            return true;
        }
        return false;
    }

    public void scanLoadedChunks(World world, String ignored) {
        Chunk[] loadedChunks = world.getLoadedChunks();
        for (Chunk c : loadedChunks) {
            scanChunk(c, ignored);
        }
    }

    public void scanChunk(Chunk chunk, String ignored) {
        if (!scannedChunks.contains(chunk)) {
            registerChunk(chunk);
            ArrayList<Material> filter = toMaterials(ignored);
            World w = chunk.getWorld();
            int cx = 16 * chunk.getX();
            int cz = 16 * chunk.getZ();

            for (int x = cx; x < cx + 16; x++) {
                for (int z = cz; z < cz + 16; z++) {
                    Location location = new Location(w, x, 0, z);
                    add(getTerrainHeight(location, filter));
                }
            }
        }
    }

    private static int getTerrainHeight(Location location, ArrayList<Material> ignored) {

        World w = location.getWorld();
        int x = location.getBlockX();
        int z = location.getBlockZ();

        int y;
        for (y = WORLD_UPPER; y >= WORLD_LOWER; y--) {
            Location l = new Location(w, x, y, z);
            Block b = l.getBlock();
            if ((!b.isPassable() || b.isLiquid()) && !ignored.contains(b.getType()))
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

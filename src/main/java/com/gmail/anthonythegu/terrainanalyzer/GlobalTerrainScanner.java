package com.gmail.anthonythegu.terrainanalyzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

public class GlobalTerrainScanner {
    private static final int WORLD_UPPER = 319;
    private static final int WORLD_LOWER = -64;

    private Main plugin;
    private FileConfiguration config;

    private final ArrayList<Material> IGNORED;
    private final ArrayList<Material> EXCLUDED;

    private World world;
    private HashSet<Chunk> scannedChunks;
    private Statistics scannerData;

    private Hashtable<Biome, Statistics> biomeData;

    public GlobalTerrainScanner(World world) {
        plugin = Main.getPlugin(Main.class);
        config = plugin.getConfig();

        IGNORED = toMaterials(config.getStringList("ignored-blocks"));
        EXCLUDED = toMaterials(config.getStringList("excluded-blocks"));

        this.world = world;
        scannedChunks = new HashSet<>();
        scannerData = new Statistics();

        biomeData = new Hashtable<>();
    }

    public void scanChunk(Chunk chunk) {
        if (chunk.getWorld() == world && !scannedChunks.contains(chunk)) {
            scannedChunks.add(chunk);
            int cx = 16 * chunk.getX();
            int cz = 16 * chunk.getZ();

            for (int x = cx; x < cx + 16; x++) {
                for (int z = cz; z < cz + 16; z++) {
                    Location location = new Location(world, x, 0, z);
                    if (!EXCLUDED.contains(location.getBlock().getType())) {

                        int th = getTerrainHeight(location);

                        scannerData.add(th);

                        Biome b = location.getBlock().getBiome();
                        Statistics biomeStatistics = biomeData.get(b);

                        if (biomeData.get(b) == null) {
                            biomeStatistics = new Statistics();
                            biomeData.put(b, biomeStatistics);
                        }

                        biomeStatistics.add(th);
                    }
                }
            }
        }
    }

    public String showStatistics() {
        StringBuilder sb = new StringBuilder();

        sb.append(ChatColor.DARK_AQUA + "[TerrainAnalyzer]" + ChatColor.RESET + " " + world.getName() + "\n");
        sb.append("Total chunks analyzed: " + scannedChunks.size() + " chunks" + "\n");
        sb.append("Total blocks analyzed: " + scannerData.count() + " blocks" + "\n");
        sb.append("World average height: " + scannerData.average() + " blocks" + "\n");
        sb.append("World mode height: " + scannerData.mode() + " blocks" + "\n");
        for (Map.Entry<Biome, Statistics> e : biomeData.entrySet()) {
            Statistics s = e.getValue();
            sb.append(e.getKey() + ": Blocks=" + s.count() + " Avg=" + s.average() + " Mode=" + s.mode() + "\n");
        }

        return sb.toString();
    }

    public void registerCommand(CommandAnalyzeTerrain command) {
        command.registerScanner(this);
    }

    private int getTerrainHeight(Location location) {

        int x = location.getBlockX();
        int z = location.getBlockZ();

        int y;
        for (y = WORLD_UPPER; y >= WORLD_LOWER; y--) {
            Location l = new Location(world, x, y, z);
            Block b = l.getBlock();
            if ((!b.isPassable() || b.isLiquid()) && !IGNORED.contains(b.getType()))
                return y;
        }
        return y;
    }

    private static ArrayList<Material> toMaterials(List<String> list) {
        ArrayList<Material> out = new ArrayList<>();
        if (list.size() > 0) {
            for (String block : list) {
                Material material = Material.matchMaterial(block);
                if (material != null)
                    out.add(material);
            }
        }
        return out;
    }
}

package com.gmail.anthonythegu.terrainanalyzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

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
                    Block block = getHighestBlock(location);

                    if (block != null && !EXCLUDED.contains(block.getType())) {
                        int height = block.getY();

                        scannerData.add(height);

                        Biome biome = location.getBlock().getBiome();
                        Statistics biomeStatistics = biomeData.get(biome);

                        if (biomeData.get(biome) == null) {
                            biomeStatistics = new Statistics();
                            biomeData.put(biome, biomeStatistics);
                        }

                        biomeStatistics.add(height);
                    }
                }
            }
        }
    }

    public String showStatistics() {
        StringBuilder sb = new StringBuilder();

        sb.append(ChatColor.DARK_AQUA + "[TerrainAnalyzer]" + ChatColor.RESET + " " + world.getName() + "\n");
        sb.append("Total chunks analyzed: " + format(scannedChunks.size()) + " chunks" + "\n");
        sb.append("Total blocks analyzed: " + format(scannerData.count()) + " blocks" + "\n");
        sb.append("World average height: " + scannerData.average() + " blocks" + "\n");
        sb.append("World mode height: " + scannerData.mode() + " blocks" + "\n");
        for (Map.Entry<Biome, Statistics> e : biomeData.entrySet()) {
            Statistics s = e.getValue();
            sb.append(ChatColor.DARK_GREEN + "" + e.getKey() + "" + ChatColor.RESET + ": Blocks=" + format(s.count()) + " Avg="
                    + s.average() + " Mode=" + s.mode() + "\n");
        }
        return sb.toString();
    }

    public void registerCommand(CommandAnalyzeTerrain command) {
        command.registerScanner(this);
    }

    private Block getHighestBlock(Location location) {

        int x = location.getBlockX();
        int z = location.getBlockZ();

        int y;
        for (y = WORLD_UPPER; y >= WORLD_LOWER; y--) {
            Location l = new Location(world, x, y, z);
            Block b = l.getBlock();
            if ((!b.isPassable() || b.isLiquid()) && !IGNORED.contains(b.getType()))
                return b;
        }
        return null;
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

    private static final NavigableMap<Long, String> suffixes = new TreeMap<> ();
    static {
      suffixes.put(1_000L, "k");
      suffixes.put(1_000_000L, "M");
      suffixes.put(1_000_000_000L, "B");
      suffixes.put(1_000_000_000_000L, "T");
      suffixes.put(1_000_000_000_000_000L, "Q");
      suffixes.put(1_000_000_000_000_000_000L, "Q");
    }
    
    public static String format(long value) {
      //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
      if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
      if (value < 0) return "-" + format(-value);
      if (value < 1000) return Long.toString(value); //deal with easy case
    
      Entry<Long, String> e = suffixes.floorEntry(value);
      Long divideBy = e.getKey();
      String suffix = e.getValue();
    
      long truncated = value / (divideBy / 10); //the number part of the output times 10
      boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
      return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}

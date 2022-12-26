package com.gmail.anthonythegu.terrainanalyzer;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TerrainScanner {
    private ArrayList<Chunk> scannedChunks;
    private RollingMode rMode;
    private static final int WORLD_LOWER = -64;

    public TerrainScanner() {
        scannedChunks = new ArrayList<>();
        rMode = new RollingMode();
    }

    public void scan(Player player) {
        Chunk chunk = player.getLocation().getChunk();

        if (chunkHasBeenScanned(chunk))
            return;

        scannedChunks.add(chunk);
        World world = player.getWorld();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Location location = new Location(world, x, 0, z);
                rMode.add(world.getHighestBlockYAt(location));
            }
        }
    }

    public static void scanChunk(Player player, ArrayList<Material> ignored) {
        Chunk chunk = player.getLocation().getChunk();
        World world = player.getWorld();
        RollingAverage rollingAverage = new RollingAverage();
        RollingMode rollingMode = new RollingMode();

        for (int x = chunk.getX(); x < 16; x++) {
            for (int z = chunk.getZ(); z < 16; z++) {
                Location location = new Location(world, x, 0, z);

                int highest = ignored.size() > 0 ? getHighestBlockFiltered(world, location, ignored)
                        : world.getHighestBlockYAt(location);

                rollingAverage.add(highest);
                rollingMode.add(highest);
            }
        }
        player.sendMessage(
                ChatColor.DARK_AQUA + "[TerrainScanner]" + ChatColor.RESET + " The average height of this chunk is "
                        + rollingAverage.get() + " blocks");
        player.sendMessage(
                ChatColor.DARK_AQUA + "[TerrainScanner]" + ChatColor.RESET + " The mode height of this chunk is "
                        + rollingMode.get() + " blocks");
    }

    private boolean chunkHasBeenScanned(Chunk chunk) {
        for (Chunk c : scannedChunks) {
            if (chunk == c)
                return true;
        }
        return false;
    }

    private static int getHighestBlockFiltered(World w, Location loc, ArrayList<Material> ignored) {
        int y;
        for (y = w.getHighestBlockYAt(loc); y >= WORLD_LOWER; y--) {
            loc.setY(y);
            if (ignored.contains(loc.getBlock().getType()))
                break;
        }
        return y;
    }
}

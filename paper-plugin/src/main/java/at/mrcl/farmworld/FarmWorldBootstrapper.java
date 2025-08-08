package at.mrcl.farmworld;

import at.mrcl.farmworld.api.Bootstrapper;
import at.mrcl.farmworld.api.FarmWorldAPI;
import at.mrcl.farmworld.database.SQLiteDatabase;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class FarmWorldBootstrapper extends Bootstrapper {

    private FarmWorldPlugin plugin;

    @Override
    public void onEnable() {
        try {
            final var database = new SQLiteDatabase(this);
            this.plugin = new FarmWorldPlugin(this, database);
            FarmWorldAPI.setApi(new APIImpl(this.plugin));
            loadFarmWorlds();
        } catch (Exception e) {
            getSLF4JLogger().error("Failed to enable plugin!", e);
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        try {
            this.plugin.disable();
        } catch (Exception e) {
            getSLF4JLogger().error("Failed to disable plugin!", e);
        }
    }

    private CompletableFuture<Void> loadFarmWorlds() {
        return CompletableFuture.runAsync(() -> {
            final var folder = getOrCreateWorldsFolder();
            getSLF4JLogger().info("Loading farm worlds in folder {}...", folder.getPath());
            createDefaultFarmWorld(folder);

            for (final var file : folder.listFiles()) {
                if (!checkWorldsFile(file)) continue;

                try {
                    new FarmWorldLoader()
                            .readFile(file)
                            .loadAndRegister();
                } catch (IOException exception) {
                    getSLF4JLogger().error("Failed to read file {}", file.getPath(), exception);
                }
            }
        });
    }

    private File getOrCreateWorldsFolder() {
        if (!getDataFolder().exists()) getDataFolder().mkdirs();
        final var folder = new File(getDataFolder(), "worlds");
        if (!folder.exists()) folder.mkdirs();
        return folder;
    }

    private boolean checkWorldsFile(File file) {
        if (file.isDirectory()) {
            getSLF4JLogger().warn("Folder {} is not a json file!", file.getPath());
            return false;
        }

        if (!file.getName().endsWith(".json")) {
            getSLF4JLogger().warn("File {} is not a json file!", file.getPath());
            return false;
        }
        return true;
    }

    private void createDefaultFarmWorld(File folder) {
        if (folder.listFiles() == null) throw new NullPointerException("Failed to load farm worlds!");
        if (folder.listFiles().length != 0) return;
        try {
            getSLF4JLogger().warn("No farm worlds found!");
            getSLF4JLogger().info("Creating default farm world.");
            new FarmWorldConfig("FarmWorld", null, World.Environment.NORMAL, null, false)
                    .save(new File(folder, "farm_world.json"));
        } catch (IOException exception) {
            getSLF4JLogger().error("Failed to create default farm world!", exception);
        }
    }
}

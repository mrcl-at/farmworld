package at.mrcl.farmworld;

import at.mrcl.farmworld.api.Bootstrapper;
import at.mrcl.farmworld.api.FarmWorld;
import at.mrcl.farmworld.api.FarmWorldAPI;
import at.mrcl.farmworld.api.database.Database;
import at.mrcl.farmworld.api.database.DatabaseException;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class FarmWorldPlugin {

    @Getter private final Bootstrapper bootstrapper;
    @Getter private final Database database;
    @Getter private final PluginConfig config;

    @Getter private final Map<String, FarmWorld> farmWorlds = new ConcurrentHashMap<>();
    @Getter private final Economy economy;

    private final AtomicBoolean enabled = new AtomicBoolean(false);

    public FarmWorldPlugin(FarmWorldBootstrapper bootstrapper, Database database, PluginConfig config) throws Exception {
        this.bootstrapper = bootstrapper;
        this.database = database;
        this.config = config;

        this.economy = setupVault();
        FarmWorldAPI.setApi(new APIImpl(this));
    }

    public void enable() throws Exception {
        if (!this.enabled.compareAndSet(false, true)) throw new IllegalStateException("Plugin is already enabled!");
        this.database.connect();
        if (economy == null) getLogger().warn("Vault not found, disabling economy support");
        loadFarmWorlds();
    }

    public void disable() throws Exception {
        if (!this.enabled.compareAndSet(true, false)) throw new IllegalStateException("Plugin is already disabled!");
        this.database.disconnect();
        this.farmWorlds.clear();
    }

    public Logger getLogger() {
        return this.bootstrapper.getSLF4JLogger();
    }

    public File getDataFolder() {
        return this.bootstrapper.getDataFolder();
    }

    private CompletableFuture<Void> loadFarmWorlds() {
        return CompletableFuture.runAsync(() -> {
            final var folder = getOrCreateWorldsFolder();
            getLogger().info("Loading farm worlds in folder {}...", folder.getPath());
            createDefaultFarmWorld(folder);

            for (final var file : folder.listFiles()) {
                if (!checkWorldsFile(file)) continue;

                try {
                    new FarmWorldLoader()
                            .readFile(file)
                            .loadAndRegister()
                            .readWorldDataFromDatabase(this)
                            .createWorldsIfEnabled();
                } catch (IOException exception) {
                    getLogger().error("Failed to read file {}", file.getPath(), exception);
                } catch (DatabaseException e) {
                    getLogger().error("Failed to execute database operation!", e);
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
            getLogger().warn("Folder {} is not a json file!", file.getPath());
            return false;
        }

        if (!file.getName().endsWith(".json")) {
            getLogger().warn("File {} is not a json file!", file.getPath());
            return false;
        }
        return true;
    }

    private void createDefaultFarmWorld(File folder) {
        if (folder.listFiles() == null) throw new NullPointerException("Failed to load farm worlds!");
        if (folder.listFiles().length != 0) return;
        try {
            getLogger().warn("No farm worlds found!");
            getLogger().info("Creating default farm world.");
            new FarmWorldConfig("FarmWorld", null, World.Environment.NORMAL, null, false)
                    .save(new File(folder, "farm_world.json"));
        } catch (IOException exception) {
            getLogger().error("Failed to create default farm world!", exception);
        }
    }

    private Economy setupVault() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) return null;
        final var provider = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (provider == null) return null;
        return provider.getProvider();
    }
}

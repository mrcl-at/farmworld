package at.mrcl.farmworld;

import at.mrcl.farmworld.api.FarmWorldAPI;
import at.mrcl.farmworld.api.WorldData;
import at.mrcl.farmworld.api.database.DatabaseException;
import at.mrcl.farmworld.util.Config;
import at.mrcl.farmworld.util.WorldUtil;

import java.io.File;
import java.io.IOException;

public class FarmWorldLoader {

    private File file;
    private FarmWorldConfig config;
    private CustomFarmWorld farmWorld;
    private FarmWorldPlugin plugin;
    private WorldData worldData;

    public FarmWorldLoader readFile(File file) throws IOException {
        this.file = file;
        this.config = Config.read(file, FarmWorldConfig.class);
        return this;
    }

    public FarmWorldLoader loadAndRegister() {
        this.farmWorld = new CustomFarmWorld(this.config, this.file);
        FarmWorldAPI.registerFarmWorld(this.farmWorld);
        return this;
    }

    public FarmWorldLoader readWorldDataFromDatabase(FarmWorldPlugin plugin) throws DatabaseException {
        this.plugin = plugin;
        this.worldData = plugin.getDatabase()
                .getWorldRepository()
                .getWorldData(this.farmWorld)
                .orElse(null);
        return this;
    }

    public FarmWorldLoader createWorldsIfEnabled() throws DatabaseException {
        if (!this.farmWorld.isEnabled()) return this;
        createWorldDataIfNotExits();
        this.farmWorld.setCurrentWorld(this.worldData);
        if (this.worldData.getWorld().isEmpty()) {
            new WorldUtil(this.plugin.getBootstrapper()).createWorld(this.farmWorld).join();
        }
        return this;
    }

    private void createWorldDataIfNotExits() throws DatabaseException {
        if (this.worldData != null) return;
        this.worldData = new WorldData(this.farmWorld.generateWorldName(), System.currentTimeMillis());
        this.plugin.getDatabase()
                .getWorldRepository()
                .createWorldDate(this.farmWorld, this.worldData);
    }
}

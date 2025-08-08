package at.mrcl.farmworld;

import at.mrcl.farmworld.api.Bootstrapper;
import at.mrcl.farmworld.api.FarmWorldAPI;
import at.mrcl.farmworld.database.SQLiteDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FarmWorldBootstrapper extends Bootstrapper {

    private FarmWorldPlugin plugin;

    @Override
    public void onEnable() {
        try {
            final var config = loadConfig();
            if (!config.isDefaultBootstrapper()) return;

            final var database = new SQLiteDatabase(this);
            this.plugin = new FarmWorldPlugin(this, database, config);
            this.plugin.enable();
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

    private PluginConfig loadConfig() throws IOException {
        final var file = new File(getDataFolder(), "config.json");
        try {
            return PluginConfig.read(file, PluginConfig.class);
        } catch (FileNotFoundException exception) {
            final var config = new PluginConfig();
            config.save(file);
            return config;
        }
    }
}

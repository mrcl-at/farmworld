package at.mrcl.farmworld;

import at.mrcl.farmworld.api.Bootstrapper;
import at.mrcl.farmworld.api.FarmWorldAPI;
import at.mrcl.farmworld.database.SQLiteDatabase;

public class FarmWorldBootstrapper extends Bootstrapper {

    private FarmWorldPlugin plugin;

    @Override
    public void onEnable() {
        try {
            final var database = new SQLiteDatabase(this);
            this.plugin = new FarmWorldPlugin(this, database);
            FarmWorldAPI.setApi(new APIImpl(this.plugin));
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
}

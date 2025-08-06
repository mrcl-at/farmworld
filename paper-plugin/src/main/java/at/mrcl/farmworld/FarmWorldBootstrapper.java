package at.mrcl.farmworld;

import at.mrcl.farmworld.api.FarmWorldAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class FarmWorldBootstrapper extends JavaPlugin {

    private FarmWorldPlugin plugin;

    @Override
    public void onEnable() {
        this.plugin = new FarmWorldPlugin(this);
        FarmWorldAPI.setApi(new APIImpl());
    }

    @Override
    public void onDisable() {
        this.plugin.disable();
    }
}

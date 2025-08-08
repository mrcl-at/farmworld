package at.mrcl.farmworld;

import at.mrcl.farmworld.api.Bootstrapper;
import at.mrcl.farmworld.api.FarmWorld;
import at.mrcl.farmworld.api.database.Database;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class FarmWorldPlugin {

    @Getter private final Bootstrapper bootstrapper;
    @Getter private final Database database;

    @Getter private final Map<String, FarmWorld> farmWorlds = new ConcurrentHashMap<>();

    private final AtomicBoolean enabled = new AtomicBoolean(false);

    public FarmWorldPlugin(FarmWorldBootstrapper bootstrapper, Database database) throws Exception {
        this.bootstrapper = bootstrapper;
        this.database = database;
    }

    public void enable() throws Exception {
        if (!this.enabled.compareAndSet(false, true)) throw new IllegalStateException("Plugin is already enabled!");
        this.database.connect();
    }

    public void disable() throws Exception {
        if (!this.enabled.compareAndSet(true, false)) throw new IllegalStateException("Plugin is already disabled!");
        this.database.disconnect();
    }

}

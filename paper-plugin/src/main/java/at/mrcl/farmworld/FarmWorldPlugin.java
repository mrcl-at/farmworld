package at.mrcl.farmworld;

import at.mrcl.farmworld.api.FarmWorld;
import at.mrcl.farmworld.api.database.Database;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FarmWorldPlugin {

    @Getter private final FarmWorldBootstrapper bootstrapper;
    @Getter private final Database database;

    @Getter private final Map<String, FarmWorld> farmWorlds = new ConcurrentHashMap<>();

    public FarmWorldPlugin(FarmWorldBootstrapper bootstrapper, Database database) throws Exception {
        this.bootstrapper = bootstrapper;
        this.database = database;
    }

    public void enable() throws Exception {
        this.database.connect();
    }

    public void disable() throws Exception {
        this.database.disconnect();
    }

}

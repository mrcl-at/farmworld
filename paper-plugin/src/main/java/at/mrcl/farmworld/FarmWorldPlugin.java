package at.mrcl.farmworld;

import at.mrcl.farmworld.api.FarmWorld;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FarmWorldPlugin {

    @Getter private final FarmWorldBootstrapper bootstrapper;

    @Getter private final Map<String, FarmWorld> farmWorlds = new ConcurrentHashMap<>();

    public FarmWorldPlugin(FarmWorldBootstrapper bootstrapper) {
        this.bootstrapper = bootstrapper;
    }

    public void enable() {

    }

    public void disable() {

    }

}

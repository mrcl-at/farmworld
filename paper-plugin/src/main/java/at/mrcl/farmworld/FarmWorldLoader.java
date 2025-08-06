package at.mrcl.farmworld;

import at.mrcl.farmworld.api.FarmWorldAPI;
import at.mrcl.farmworld.util.Config;

import java.io.File;
import java.io.IOException;

public class FarmWorldLoader {

    private File file;
    private FarmWorldConfig config;
    private CustomFarmWorld farmWorld;

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
}

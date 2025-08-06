package at.mrcl.farmworld;

import at.mrcl.farmworld.api.FarmWorld;
import at.mrcl.farmworld.api.FarmWorldAPI;
import lombok.AllArgsConstructor;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

@AllArgsConstructor
public class FarmWorldBuilder implements FarmWorld.Builder {

    private final File dataFolder;
    private final FarmWorldConfig config = new FarmWorldConfig();

    @Override
    public FarmWorld.Builder name(@NotNull String name) {
        this.config.setName(name);
        return this;
    }

    @Override
    public FarmWorld.Builder permission(@Nullable String permission) {
        this.config.setPermission(permission);
        return this;
    }

    @Override
    public FarmWorld.Builder environment(World.@NotNull Environment environment) {
        this.config.setEnvironment(environment);
        return this;
    }

    @Override
    public FarmWorld.Builder generator(@NotNull String generator) {
        this.config.setGenerator(generator);
        return this;
    }

    @Override
    public FarmWorld.Builder enabled(boolean enabled) {
        this.config.setEnabled(enabled);
        return this;
    }

    @Override
    public FarmWorld build() {
        checkConfig();
        final var folder = new File(this.dataFolder, "worlds");
        if (!folder.exists()) folder.mkdirs();
        return new CustomFarmWorld(this.config, new File(folder, this.config.getName().replace(" ", "_").toLowerCase() + ".json"));
    }

    @Override
    public FarmWorld buildAndRegister() {
        final var world = build();
        FarmWorldAPI.registerFarmWorld(world);
        return world;
    }

    private void checkConfig() {
        if (this.config.getName() == null) throw new IllegalStateException("Name is null");
        if (this.config.getEnvironment() == null) throw new IllegalStateException("Environment is null");
        if (this.config.getGenerator() == null) throw new IllegalStateException("Generator is null");
    }
}

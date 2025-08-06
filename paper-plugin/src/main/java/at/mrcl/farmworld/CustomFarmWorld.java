package at.mrcl.farmworld;

import at.mrcl.farmworld.api.FarmWorld;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Optional;

public class CustomFarmWorld implements FarmWorld {

    private final FarmWorldConfig config;
    private final File file;

    public CustomFarmWorld(FarmWorldConfig config, File file) {
        this.config = config;
        this.file = file;
    }

    @Override
    public @NotNull String getName() {
        return this.config.getName();
    }

    @Override
    public @Nullable String getPermission() {
        return this.config.getPermission();
    }

    @Override
    public @NotNull World.Environment getEnvironment() {
        return this.config.getEnvironment();
    }

    @Override
    public @NotNull String getGenerator() {
        return this.config.getGenerator();
    }

    @Override
    public boolean isEnabled() {
        return this.config.isEnabled();
    }

    @Override
    public @NotNull Optional<File> getFile() {
        return Optional.ofNullable(this.file);
    }
}

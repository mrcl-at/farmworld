package at.mrcl.farmworld;

import at.mrcl.farmworld.api.FarmWorld;
import at.mrcl.farmworld.api.FarmWorldAPI;
import at.mrcl.farmworld.api.WorldData;
import lombok.Setter;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

public class CustomFarmWorld implements FarmWorld {

    private final FarmWorldConfig config;
    private final File file;

    @Setter
    private WorldData currentWorld;

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
    public boolean useSeed() {
        return this.config.isUseSeed();
    }

    @Override
    public long getSeed() {
        return this.config.getSeed();
    }

    @Override
    public @NotNull String generateWorldName() {
        return FarmWorldAPI.hasStaticWorldNames() ? getName() : getName() + "-" + UUID.randomUUID().toString().split("-")[0];
    }

    @Override
    public boolean isEnabled() {
        return this.config.isEnabled();
    }

    @Override
    public @NotNull Optional<File> getFile() {
        return Optional.ofNullable(this.file);
    }

    @Override
    public Optional<WorldData> getCurrentWorld() {
        return Optional.ofNullable(this.currentWorld);
    }
}

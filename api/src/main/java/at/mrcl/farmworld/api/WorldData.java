package at.mrcl.farmworld.api;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record WorldData(@NotNull String name, long created) {
    public Optional<World> getWorld() {
        return Optional.ofNullable(Bukkit.getWorld(this.name()));
    }
}

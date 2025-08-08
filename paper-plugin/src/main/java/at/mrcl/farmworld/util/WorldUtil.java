package at.mrcl.farmworld.util;

import at.mrcl.farmworld.api.Bootstrapper;
import at.mrcl.farmworld.api.FarmWorld;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class WorldUtil {

    private final Bootstrapper bootstrapper;

    public CompletableFuture<World> createWorld(FarmWorld farmWorld) {
        final var future = new CompletableFuture<World>();
        Bukkit.getScheduler().runTask(this.bootstrapper, () -> {
            try {
                final var creator = new WorldCreator(farmWorld.generateWorldName());
                creator.environment(farmWorld.getEnvironment());
                if (farmWorld.getGenerator() != null) creator.generator(farmWorld.getGenerator());
                future.complete(creator.createWorld());
            } catch (Exception exception) {
                future.completeExceptionally(exception);
            }
        });
        return future;
    }
}

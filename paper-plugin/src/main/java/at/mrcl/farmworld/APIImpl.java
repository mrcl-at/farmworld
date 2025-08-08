package at.mrcl.farmworld;

import at.mrcl.farmworld.api.API;
import at.mrcl.farmworld.api.FarmWorld;
import at.mrcl.farmworld.api.event.FarmWorldRegisterEvent;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class APIImpl implements API {

    private final FarmWorldPlugin plugin;

    @Override
    public boolean hasStaticWorldNames() {
        return this.plugin.getConfig().isStaticWorldNames();
    }

    @Override
    public void registerFarmWorld(@NotNull FarmWorld world) {
        this.plugin.getFarmWorlds().put(world.getName(), world);
        new FarmWorldRegisterEvent(world).callEvent();
    }

    @Override
    public @NotNull FarmWorld.Builder farmWorldBuilder() {
        return new FarmWorldBuilder(this.plugin.getBootstrapper().getDataFolder());
    }
}

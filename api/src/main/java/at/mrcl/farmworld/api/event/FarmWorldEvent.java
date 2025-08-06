package at.mrcl.farmworld.api.event;

import at.mrcl.farmworld.api.FarmWorld;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an event that is associated with a specific {@link FarmWorld}.
 * This is an abstract base class for all events that involve a farm world.
 */
public abstract class FarmWorldEvent extends Event {

    private final FarmWorld world;

    public FarmWorldEvent(FarmWorld world) {
        this.world = world;
    }

    public FarmWorldEvent(boolean isAsync, FarmWorld world) {
        super(isAsync);
        this.world = world;
    }

    /**
     * Retrieves the {@link FarmWorld} associated with this event.
     *
     * @return the associated FarmWorld, never null
     */
    public @NotNull FarmWorld getWorld() {
        return world;
    }
}

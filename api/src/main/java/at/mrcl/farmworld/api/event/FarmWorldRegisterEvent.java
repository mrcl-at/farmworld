package at.mrcl.farmworld.api.event;

import at.mrcl.farmworld.api.FarmWorld;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an event that is triggered when a {@link FarmWorld} is registered.
 * This event is fired whenever a new farm world is added or registered within
 * the system.
 *
 * This class extends {@link FarmWorldEvent}, inheriting properties such as the
 * associated {@link FarmWorld}.
 *
 * The event can be triggered synchronously or asynchronously, depending on
 * the specific constructor used.
 */
public class FarmWorldRegisterEvent extends FarmWorldEvent {

    private static final HandlerList handlers = new HandlerList();

    public FarmWorldRegisterEvent(FarmWorld world) {
        super(world);
    }

    public FarmWorldRegisterEvent(boolean isAsync, FarmWorld world) {
        super(isAsync, world);
    }

    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}

package at.mrcl.farmworld.api;

import org.jetbrains.annotations.NotNull;

public interface API {

    /**
     * Registers the provided {@link FarmWorld} instance.
     *
     * @param world The {@link FarmWorld} to be registered. Must not be null.
     */
    void registerFarmWorld(@NotNull FarmWorld world);

    /**
     * Provides a builder instance to construct a new {@link FarmWorld}.
     *
     * @return a {@link FarmWorld.Builder} instance for creating and configuring a {@link FarmWorld}
     */
    @NotNull FarmWorld.Builder farmWorldBuilder();

}

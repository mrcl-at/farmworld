package at.mrcl.farmworld.api;

import org.jetbrains.annotations.NotNull;

public class FarmWorldAPI {

    private static API api;

    public static void setApi(API api) {
        if (FarmWorldAPI.api != null) {
            throw new IllegalStateException("API already set");
        }
        FarmWorldAPI.api = api;
    }

    public static API getApi() {
        return api;
    }

    /**
     * Registers the provided {@link FarmWorld} instance.
     *
     * @param world The {@link FarmWorld} to be registered. Must not be null.
     */
    public static void registerFarmWorld(@NotNull FarmWorld world) {
        api.registerFarmWorld(world);
    }

    /**
     * Provides a builder instance to construct a new {@link FarmWorld}.
     *
     * @return a {@link FarmWorld.Builder} instance for creating and configuring a {@link FarmWorld}
     */
    public static @NotNull FarmWorld.Builder farmWorldBuilder() {
        return api.farmWorldBuilder();
    }
}

package at.mrcl.farmworld.api;

import org.jetbrains.annotations.NotNull;

public interface API {

    /**
     * Determines whether static world names are enabled.
     *
     * @return true if static world names are enabled, false otherwise
     */
    boolean hasStaticWorldNames();

    /**
     * Determines if the vault functionality is supported by the current API implementation.
     *
     * @return true if the vault functionality is supported, false otherwise
     */
    boolean isVaultSupported();

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

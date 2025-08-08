package at.mrcl.farmworld.api;

import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Optional;

public interface FarmWorld {

    /**
     * Retrieves the name of the farm world.
     *
     * @return the non-null name of the farm world
     */
    @NotNull String getName();

    /**
     * Retrieves the permission associated with the FarmWorld, if any.
     * The permission may be used to restrict access or functionality within the FarmWorld.
     *
     * @return the permission if one is defined, or null if no permission is set
     */
    @Nullable String getPermission();

    /**
     * Retrieves the environment of the {@link FarmWorld}.
     *
     * @return the {@link World.Environment} associated with the {@link FarmWorld}, never null.
     */
    @NotNull World.Environment getEnvironment();


    /**
     * Retrieves the generator associated with the FarmWorld, if any.
     *
     * @return the generator if defined, or null if no generator is set
     */
    @Nullable String getGenerator();

    /**
     * Generates a unique or static name for the farm world.
     * If static world names are enabled, the generated name will be the name of the farm world.
     * Otherwise, a unique identifier is appended to the name to ensure uniqueness.
     *
     * @return the generated name of the farm world, never null
     */
    @NotNull String generateWorldName();

    /**
     * Checks whether the FarmWorld instance is enabled.
     *
     * @return true if the instance is enabled, false otherwise
     */
    boolean isEnabled();

    /**
     * Retrieves the file associated with the {@link FarmWorld} instance.
     *
     * @return an {@link Optional} containing the associated {@link File} if present, otherwise an empty {@link Optional}.
     */
    Optional<File> getFile();

    /**
     * Retrieves the current {@link WorldData} associated with the {@link FarmWorld}, if one exists.
     * The returned {@link WorldData} contains metadata about the current world, such as its name and creation timestamp.
     *
     * @return an {@link Optional} containing the current {@link WorldData} if a world is associated, or an empty {@link Optional} if no world is currently set
     */
    Optional<WorldData> getCurrentWorld();

    interface Builder {
        /**
         * Sets the name for the {@link FarmWorld}.
         * Must be set.
         *
         * @see FarmWorld#getName()
         */
        Builder name(@NotNull String name);
        /**
         * Sets the permission for the {@link FarmWorld}.
         *
         * @see FarmWorld#getPermission()
         **/
        Builder permission(@Nullable String permission);
        /**
         * Sets the environment for the {@link FarmWorld}.
         * Must be set.
         *
         * @see FarmWorld#getEnvironment()
         */
        Builder environment(@NotNull World.Environment environment);
        /**
         * Sets the generator for the {@link FarmWorld}.
         *
         * @see FarmWorld#getGenerator()
         * */
        Builder generator(@NotNull String generator);
        /**
         * Sets whether the {@link FarmWorld} is enabled.
         * The default is false.
         *
         * @see FarmWorld#isEnabled()
         */
        Builder enabled(boolean enabled);

        /**
         * Constructs a new {@link FarmWorld} instance using the builder
         */
        FarmWorld build();

        /**
         * Builds a new {@link FarmWorld} instance and registers it.
         * This method combines construction and registration steps into a single action,
         * ensuring the created {@link FarmWorld}
         *
         * @see FarmWorld.Builder#build()
         * @see FarmWorldAPI#registerFarmWorld(FarmWorld)
         */
        FarmWorld buildAndRegister();
    }
}

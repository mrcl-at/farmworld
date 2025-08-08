package at.mrcl.farmworld.api.database;

import at.mrcl.farmworld.api.FarmWorld;
import at.mrcl.farmworld.api.WorldData;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Represents a repository for managing world-related data.
 *
 * The purpose of this interface is to define the structure and behavior for
 * interacting with data that pertains to "worlds". Implementations of this
 * interface should provide specific mechanisms for handling persistent storage
 * and retrieval of world information, enabling access and manipulation of
 * stored data.
 *
 * This interface is typically used in conjunction with a database connection,
 * and might be acquired via methods such as {@code Database#getWorldRepository()}.
 *
 * Classes implementing this interface should ensure proper handling of any
 * database interactions, including error management and efficient data access.
 */
public interface WorldRepository {

    /**
     * Persists the provided {@link FarmWorld} and its associated {@link WorldData} into the database.
     * This method is responsible for storing metadata about a specific farm world, including its name
     * and creation timestamp, to ensure it can be accessed and retrieved in the future.
     *
     * @param farmWorld the {@link FarmWorld} instance to be stored. Must not be null.
     * @param worldData the {@link WorldData} object containing metadata information for the farm world. Must not be null.
     * @throws DatabaseException if an error occurs during the database interaction or persistence process.
     */
    void createWorldDate(@NotNull FarmWorld farmWorld, @NotNull WorldData worldData) throws DatabaseException;

    /**
     * Retrieves the world data associated with the given {@code FarmWorld}.
     * This method interacts with the database to fetch metadata about the specified farm world,
     * such as its name and creation timestamp. If the world data is not found, an empty {@link Optional}
     * is returned.
     *
     * @param farmWorld the {@link FarmWorld} instance for which the data is being retrieved.
     *                  Must not be null.
     *
     * @return an {@link Optional} containing the {@link WorldData} if found, or an empty {@link Optional} if no data exists for the given {@link FarmWorld}.
     * @throws DatabaseException if an error occurs while accessing the database.
     */
    Optional<WorldData> getWorldData(@NotNull FarmWorld farmWorld) throws DatabaseException;
}

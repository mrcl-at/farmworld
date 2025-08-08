package at.mrcl.farmworld.api.database;

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
}

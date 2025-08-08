package at.mrcl.farmworld.api.database;

/**
 * Represents a generic interface for database operations, providing a structure
 * for connecting and disconnecting from a database.
 *
 * Classes implementing this interface must define the mechanisms for establishing
 * and closing a connection to a specific type of database.
 */
public interface Database {
    /**
     * Establishes a connection to the database.
     *
     * This method is responsible for initiating a connection to a specific database.
     * Implementations should ensure proper connection handling, including validation
     * of necessary parameters and setup of any required resources.
     *
     * @throws DatabaseException if an error occurs while attempting to connect to the database.
     */
    void connect() throws DatabaseException;
    /**
     * Closes the connection to the database.
     *
     * Implementations of this method should terminate any active connections
     * to the database and release any resources associated with the connection.
     * Once this method is invoked, no further operations should be performed
     * on the database connection until it is re-established using the connect method.
     *
     * @throws DatabaseException if an error occurs while disconnecting from the database.
     */
    void disconnect() throws DatabaseException;
}

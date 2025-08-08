package at.mrcl.farmworld.database;

import at.mrcl.farmworld.api.database.Database;
import at.mrcl.farmworld.api.database.DatabaseException;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDatabase implements Database {

    private static final String PREFIX = "jdbc:sqlite:";

    private final String jdbcUrl;
    /**
     * -- GETTER --
     *  Exposes the underlying JDBC connection for callers that need it.
     *  Returns null if not connected.
     */
    @Getter
    private Connection connection;

    /**
     * Creates a SQLite database using a default file name in the working directory.
     */
    public SQLiteDatabase(JavaPlugin plugin) {
        this(new File(plugin.getDataFolder(), "farmworld.db").getPath());
    }

    /**
     * Creates a SQLite database pointing at the given file path.
     * Accepts either a plain path (e.g. plugins/FarmWorld/farmworld.db) or a full jdbc:sqlite: URL.
     *
     * @param filePath path to the SQLite database file, or a jdbc:sqlite: URL
     */
    public SQLiteDatabase(String filePath) {
        this.jdbcUrl = filePath.startsWith("jdbc:sqlite:") ? filePath : "jdbc:sqlite:" + filePath;
    }

    @Override
    public void connect() throws DatabaseException {
        // If already connected and not closed, do nothing.
        if (this.connection != null) {
            try {
                if (!this.connection.isClosed()) {
                    return;
                }
            } catch (SQLException exception) {
                throw new DatabaseException("Failed to check if SQLite connection is closed: " + exception.getMessage(), exception);
            }
        }

        final var path = extractFilePath(jdbcUrl);
        if (path != null && !":memory:".equals(path)) {
            final var dbFile = new File(path);
            final var parent = dbFile.getAbsoluteFile().getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                throw new DatabaseException("Failed to create directories for SQLite database at " + parent);
            }
        }

        try {
            this.connection = DriverManager.getConnection(jdbcUrl);

            this.connection.setAutoCommit(true);
            try (Statement st = this.connection.createStatement()) {
                st.execute("PRAGMA foreign_keys = ON;");
                st.execute("PRAGMA journal_mode = WAL;");
                st.execute("PRAGMA synchronous = NORMAL;");
                st.execute("PRAGMA busy_timeout = 5000;");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to connect to SQLite database: " + e.getMessage());
        }
    }

    @Override
    public void disconnect() throws DatabaseException {
        if (this.connection == null) {
            return;
        }

        try {
            if (!this.connection.getAutoCommit()) {
                this.connection.commit();
                this.connection.rollback();
            }
        } catch (SQLException e) {
            try {
                this.connection.close();
            } catch (SQLException ignored) {
            } finally {
                this.connection = null;
            }
            throw new DatabaseException("Error while finalizing SQLite connection: " + e.getMessage());
        }

        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to close SQLite database connection: " + e.getMessage());
        } finally {
            this.connection = null;
        }
    }

    /**
     * Extracts the file path from a jdbc:sqlite: URL, if any.
     * Returns null for non-SQLite URLs or if no path is present.
     */
    private static String extractFilePath(String url) {
        if (!url.startsWith(PREFIX)) return null;
        String path = url.substring(PREFIX.length());
        int q = path.indexOf('?');
        if (q >= 0) path = path.substring(0, q);
        path = path.trim();
        if (path.isEmpty()) return null;
        return path;
    }
}

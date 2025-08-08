package at.mrcl.farmworld.database;

import at.mrcl.farmworld.api.FarmWorld;
import at.mrcl.farmworld.api.WorldData;
import at.mrcl.farmworld.api.database.DatabaseException;
import at.mrcl.farmworld.api.database.WorldRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class SQLiteWorldRepository implements WorldRepository {

    private final SQLiteDatabase database;

    public SQLiteWorldRepository(SQLiteDatabase database) throws DatabaseException {
        try {
            this.database = database;
            database.getConnection()
                    .createStatement()
                    .executeUpdate("""
                CREATE TABLE IF NOT EXISTS worlds (
                    farm_world TEXT NOT NULL PRIMARY KEY,
                    name TEXT NOT NULL,
                    created INTEGER NOT NULL
                )
                 """);
        } catch (Exception exception) {
            throw new DatabaseException("Failed to initialize SQLite world repository", exception);
        }
    }

    @Override
    public void createWorldDate(FarmWorld farmWorld, WorldData worldData) throws DatabaseException {
        try {
            final PreparedStatement statement = this.database.getConnection()
                    .prepareStatement("INSERT INTO worlds(farm_world, name, created) VALUES (?, ?, ?) ON CONFLICT (farm_world) DO UPDATE SET name = excluded.world, created = excluded.created");
            statement.setString(1, farmWorld.getName());
            statement.setString(2, worldData.name());
            statement.setLong(3, worldData.created());
            statement.execute();
        } catch (SQLException exception) {
            throw new DatabaseException(exception);
        }
    }

    @Override
    public Optional<WorldData> getWorldData(FarmWorld farmWorld) throws DatabaseException {
        try {
            final PreparedStatement statement = this.database.getConnection()
                    .prepareStatement("SELECT name, created FROM worlds WHERE farm_world = ?");
            statement.setString(1, farmWorld.getName());
            try (final var resultSet = statement.executeQuery()) {
                if (!resultSet.next()) return Optional.empty();
                return Optional.of(new WorldData(resultSet.getString("name"), resultSet.getLong("created")));
            }
        } catch (Exception exception) {
            throw new DatabaseException(exception);
        }
    }
}

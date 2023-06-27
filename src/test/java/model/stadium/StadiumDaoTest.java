package model.stadium;

import model.stadium.Stadium;
import model.stadium.StadiumDao;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StadiumDaoTest {
    private static final String DB_URL = "jdbc:mysql://localhost/baseball";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root1234";

    private static Connection connection;
    private StadiumDao stadiumDao;

    @BeforeAll
    public static void setupConnection() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @BeforeEach
    public void setup() {
        stadiumDao = new StadiumDao(connection);
    }

    @Test
    public void testCreateStadium() throws SQLException {
        // Given
        int id = 1;
        String name = "Stadium 1";

        // When
        int result = stadiumDao.createStadium(id, name);

        // Then
        assertEquals(1, result, "Creating a stadium should return 1");

        List<Stadium> stadiums = stadiumDao.getAllStadiums();
        assertNotNull(stadiums, "Stadium list should not be null");
        assertEquals(1, stadiums.size(), "There should be 1 stadium in the list");

        Stadium createdStadium = stadiums.get(0);
        assertEquals(id, createdStadium.getId(), "Stadium ID should match");
        assertEquals(name, createdStadium.getName(), "Stadium name should match");
    }
}


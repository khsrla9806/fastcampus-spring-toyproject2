package model.team;

import model.dto.TeamRespDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class TeamDaoTest {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/baseball";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root1234";

    private Connection connection;
    private TeamDao teamDao;

    @BeforeEach
    public void setup() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        teamDao = new TeamDao(connection);
    }

    @Test
    void testCreateTeam() throws SQLException {
        // Given
        int id = 1;
        int stadiumId = 1;
        String name = "Team A";

        // When
        int result = teamDao.createTeam(id, stadiumId, name);

        // Then
        Assertions.assertEquals(1, result);
    }

    @Test
    public void testGetAllTeams() {
        // Given
        int id = 1;

        // When
        List<TeamRespDto> teams = teamDao.getAllTeams(id);

        // Then
        Assertions.assertNotNull(teams);
        Assertions.assertFalse(teams.isEmpty());
        for (TeamRespDto team : teams) {
            Assertions.assertNotNull(team.getId());
            Assertions.assertNotNull(team.getStadiumId());
            Assertions.assertNotNull(team.getName());
            Assertions.assertNotNull(team.getCreatedAt());
        }
    }
}

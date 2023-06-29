package model.team;

import dto.TeamRespDto;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDao {
    private Connection connection;

    public TeamDao(Connection connection) {
        this.connection = connection;
    }

    public int createTeam(int stadiumId, String name) throws SQLException {
        String query = "INSERT INTO team (stadium_id, name, created_at) VALUES(?,?,now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, stadiumId);
            statement.setString(2, name);

            return statement.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    public List<TeamRespDto> getAllTeams() {
        List<TeamRespDto> teams = new ArrayList<>();
        String query = "SELECT t.id AS team_id, t.name AS team_name, s.id AS stadium_id, s.name AS stadium_name " +
                "FROM team t " +
                "INNER JOIN stadium s ON t.stadium_id = s.id";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                teams.add(getTeamFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return teams;
    }

    private TeamRespDto getTeamFromResultSet(ResultSet resultSet) {
        try {
            Integer teamId = resultSet.getInt("team_id");
            Integer stadiumId = resultSet.getInt("stadium_id");
            String teamName = resultSet.getString("team_name");
            String stadiumName = resultSet.getString("stadium_name");

            return TeamRespDto.builder()
                    .id(teamId)
                    .stadiumId(stadiumId)
                    .teamName(teamName)
                    .stadiumName(stadiumName)
                    .build();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

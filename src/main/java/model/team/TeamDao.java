package model.team;

import model.dto.TeamRespDto;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDao {
    private Connection connection;

    public TeamDao(Connection connection) {
        this.connection = connection;
    }
    //팀등록
    public int createTeam(int id, int stadiumId, String name) throws SQLException {
        String query = "INSERT INTO team (id, stadium_id, name, created_at) VALUES(?,?,?,now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setInt(2, stadiumId);
            statement.setString(3, name);

            return statement.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    //전체 팀 목록 보기
    public List<TeamRespDto> getAllTeams(int id){
        List<TeamRespDto> teams = new ArrayList<>();
        String query ="SELECT t.id AS team_id, t.name AS team_name, s.id AS stadium_id, t.created_at AS created_at  " +
                "FROM team t " +
                "RIGHT OUTER JOIN stadium s ON t.stadium_id = s.id";


        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    teams.add(getTeamFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return teams;
    }
    private TeamRespDto getTeamFromResultSet(ResultSet resultSet) {
        try {
            Integer id = resultSet.getInt("id");
            Integer stadiumId = resultSet.getInt("stadium_id");
            String name = resultSet.getString("name");
            Timestamp createdAt = resultSet.getTimestamp("created_at");

            return TeamRespDto.builder()
                    .id(id)
                    .stadiumId(stadiumId)
                    .name(name)
                    .createdAt(createdAt)
                    .build();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

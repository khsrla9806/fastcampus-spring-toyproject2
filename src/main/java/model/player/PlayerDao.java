package model.player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao {
    private Connection connection;

    public PlayerDao(Connection connection) {
        this.connection = connection;
    }

    // 선수 등록
    public int createPlayer(int teamId, String playerName, String position) {
        String query = "INSERT INTO player (team_id, name, position, created_at) VALUES (?, ?, ?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teamId);
            statement.setString(2, playerName);
            statement.setString(3, position);

            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;

        // TODO : 발생할 수 있는 예외 사항
        // 1. teamId에 해당하는 팀이 존재하지 않는 경우
        // 2. 이미 해당 팀에 존재하는 position인 경우
    }

    // 선수 조회 (id 값으로 조회)
    public Player getPlayerById(int id) {
        String query = "SELECT * FROM player WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultset = statement.executeQuery()) {
                if (resultset.next()) {
                    return getPlayerFromResultSet(resultset);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // 팀별 선수 목록 조회
    public List<Player> getPlayersByTeamId(int teamId) {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM player WHERE team_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teamId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    players.add(getPlayerFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return players;
    }

    // 선수 퇴출
    public int convertOutPlayer(int playerId) {
        String query = "UPDATE player SET team_id = null where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playerId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    private Player getPlayerFromResultSet(ResultSet resultSet) {
        try {
            Integer id = resultSet.getInt("id");
            Integer teamId = resultSet.getInt("team_id");
            teamId = teamId == 0 ? null : teamId;
            String name = resultSet.getString("name");
            String position = resultSet.getString("position");
            Timestamp createdAt = resultSet.getTimestamp("created_at");

            return Player.builder()
                    .id(id)
                    .teamId(teamId)
                    .name(name)
                    .position(position)
                    .createdAt(createdAt)
                    .build();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

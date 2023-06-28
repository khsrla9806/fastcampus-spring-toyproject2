package model.player;

import lombok.Getter;
import model.dto.PositionRespDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PlayerDao {
    private final Connection connection;

    public PlayerDao(Connection connection) {
        this.connection = connection;
    }

    public int createPlayer(int teamId, String playerName, String position) {
        String query = "INSERT INTO player (team_id, name, position, created_at) VALUES (?, ?, ?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teamId);
            statement.setString(2, playerName);
            statement.setString(3, position);

            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("선수 등록 실패");
        }

        return -1;
    }

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
            System.out.println("선수 조회 실패");
        }
        return null;
    }

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
            System.out.println("팀별 선수 목록 조회 실패");
        }

        return players;
    }

    public int convertOutPlayer(int playerId) {
        String query = "UPDATE player SET team_id = null where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playerId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("선수 퇴출 오류");
        }

        return -1;
    }

    public PositionRespDto getAllPlayersPerPosition() {
        PositionRespDto positionRespDto = new PositionRespDto();
        List<String> teams = getTeamNames();
        if (teams == null || teams.size() == 0) {
            return null;
        }
        positionRespDto.setTeams(teams);
        String query = GenerateQueryUsingTeams(teams);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String position = resultSet.getString("포지션");
                    for (String name : teams) {
                        String playerName = resultSet.getString(name);
                        positionRespDto.setPositionAndPlayerName(position, playerName);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("포지션별 목록 조회 실패");
        }

        return positionRespDto;
    }

    private String GenerateQueryUsingTeams(List<String> teams) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append("position '포지션', ");
        for (int index = 0; index < teams.size(); index++) {
            String name = teams.get(index);
            builder.append(String.format("MAX(CASE WHEN team_name = '%s' THEN player_name END) '%s'", name, name));
            builder.append(index != teams.size() - 1 ? ", " : " ");
        }
        builder.append("FROM ( " +
                "SELECT p.name 'player_name', p.position 'position', t.name 'team_name' " +
                "FROM player p " +
                "INNER JOIN team t " +
                "ON p.team_id = t.id ) pt " +
                "GROUP BY position;");

        return builder.toString();
    }

    private List<String> getTeamNames() {
        List<String> teams = new ArrayList<>();
        String query = "SELECT name FROM team";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                   teams.add(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return teams;
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

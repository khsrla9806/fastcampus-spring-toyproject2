package model.outplayer;

import lombok.Getter;
import model.dto.OutPlayerRespDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OutPlayerDao {

    private final Connection connection;

    public OutPlayerDao(Connection connection) {
        this.connection = connection;
    }

    // 퇴출 선수 등록
    public int createOutPlayer(int playerId, String reason) {
        String query = "INSERT INTO out_player (player_id, reason, created_at) VALUES (?, ?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playerId);
            statement.setString(2, reason);

            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    // 퇴출 선수 목록 조회
    public List<OutPlayerRespDto> getOutPlayers() {
        List<OutPlayerRespDto> outPlayers = new ArrayList<>();
        String query = "SELECT p.id 'id', p.name 'name', p.position 'position', o.reason 'reason', o.created_at 'outDate' " +
                "FROM out_player o " +
                "RIGHT OUTER JOIN player p " +
                "ON o.player_id = p.id";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    outPlayers.add(getOutPlayerRespDtoFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return outPlayers;
    }

    private OutPlayerRespDto getOutPlayerRespDtoFromResultSet(ResultSet resultSet) {
        try {
            Integer playerId = resultSet.getInt("id");
            String playerName = resultSet.getString("name");
            String playerPosition = resultSet.getString("position");
            String outReason = resultSet.getString("reason");
            Timestamp outDate = resultSet.getTimestamp("outDate");

            return OutPlayerRespDto.builder()
                    .playerId(playerId)
                    .playerName(playerName)
                    .playerPosition(playerPosition)
                    .outReason(outReason)
                    .outDate(outDate)
                    .build();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}

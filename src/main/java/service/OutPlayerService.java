package service;

import dto.OutPlayerRespDto;
import model.outplayer.OutPlayerDao;
import model.player.PlayerDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OutPlayerService {

    private final PlayerDao playerDao;
    private final OutPlayerDao outPlayerDao;

    public OutPlayerService(PlayerDao playerDao, OutPlayerDao outPlayerDao) {
        this.playerDao = playerDao;
        this.outPlayerDao = outPlayerDao;
    }

    public String createOutPlayer(int playerId, String reason) {
        Connection connection = playerDao.getConnection();

        try {
            connection.setAutoCommit(false);

            int outPlayerResult = outPlayerDao.createOutPlayer(playerId, reason);
            int convertResult = playerDao.convertOutPlayer(playerId);

            if (outPlayerResult == -1 || convertResult == -1) {
                connection.rollback();
            } else {
                connection.commit();
                return "성공";
            }
        } catch (SQLException outerException) {
            System.out.println("ERROR: " + outerException.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        return "실패";
    }

    public List<OutPlayerRespDto> getOutPlayers() {
        List<OutPlayerRespDto> outPlayers = outPlayerDao.getOutPlayers();
        if (outPlayers == null) {
            throw new RuntimeException("퇴출 선수 목록을 가져올 수 없습니다.");
        }
        return outPlayers;
    }

}

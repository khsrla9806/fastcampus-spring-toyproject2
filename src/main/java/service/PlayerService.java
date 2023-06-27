package service;

import model.dto.PositionRespDto;
import model.player.Player;
import model.player.PlayerDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PlayerService {

    private final PlayerDao playerDao;

    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    // 선수 등록
    public String createPlayer(int teamId, String name, String position) {
        Connection connection = playerDao.getConnection();

        try {
            connection.setAutoCommit(false);
            int result = playerDao.createPlayer(teamId, name, position);
            if (result == -1) {
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


    // 팀별 선수 조회 (nullable)
    public List<Player> getPlayersByTeamId(int teamId) {
        List<Player> players = playerDao.getPlayersByTeamId(teamId);
        if (players == null) {
            throw new RuntimeException("선수 목록을 가져올 수 없습니다.");
        }

        return players;
    }


    // 포지션 별 팀 야구선수 조회
    public PositionRespDto getPlayersPerPosition() {
        PositionRespDto positionRespDto = playerDao.getAllPlayersPerPosition();
        if (positionRespDto == null) {
            throw new RuntimeException("포지션 별 팀 야구선수 페이지를 불러올 수 없습니다.");
        }

        return positionRespDto;
    }
}

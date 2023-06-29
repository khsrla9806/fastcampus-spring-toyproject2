package service;

import dto.PositionRespDto;
import model.player.Player;
import model.player.PlayerDao;

import java.util.List;

public class PlayerService {

    private final PlayerDao playerDao;

    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    // 선수 등록
    public String createPlayer(int teamId, String name, String position) {
        int result = playerDao.createPlayer(teamId, name, position);

        return result == -1 ? "실패" : "성공";
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

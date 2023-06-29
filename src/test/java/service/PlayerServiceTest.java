package service;

import db.DBConnection;
import dto.PositionRespDto;
import model.player.Player;
import model.player.PlayerDao;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerServiceTest {
     private Connection connection = DBConnection.getConnection();
     private PlayerDao playerDao = new PlayerDao(connection);
     private PlayerService playerService = new PlayerService(playerDao);

    @BeforeEach
    void beforeEach() throws SQLException {
        dbInit();
    }

    @AfterEach
    void afterEach() throws SQLException {
        dbInit();
    }

    private void dbInit() throws SQLException {
        connection.prepareStatement("DELETE FROM player").execute();
        connection.prepareStatement("alter table player auto_increment=1").execute();
    }

    @Test
    @DisplayName("Service - 선수 등록 성공")
    void createPlayerSuccessTest() {
        // Given
        int teamId = 1;
        String name = "일대호";
        String position = "1루수";

        // When
        String result = playerService.createPlayer(teamId, name, position);

        // Then
        assertThat(result).isEqualTo("성공");
    }

    @Test
    @DisplayName("Service - 선수 등록 실패 (중복 포지션)")
    void createPlayerFailTestWithDuplicatePosition() {
        // Given
        playerDao.createPlayer(1, "이대호", "1루수");
        int teamId = 1;
        String name = "일대호";
        String position = "1루수";

        // When
        String result = playerService.createPlayer(teamId, name, position);

        // Then
        assertThat(result).isEqualTo("실패");
    }

    @Test
    @DisplayName("Service - 선수 등록 실패 (존재하지 않는 팀Id)")
    void createPlayerFailTestWithInvalidTeamId() {
        // Given
        int teamId = -1;
        String name = "일대호";
        String position = "1루수";

        // When
        String result = playerService.createPlayer(teamId, name, position);

        // Then
        assertThat(result).isEqualTo("실패");
    }

    @Test
    @DisplayName("Service - 팀 조회 성공")
    void getPlayersByTeamIdSuccessTest() {
        // Given
        int teamId = 1;
        playerDao.createPlayer(teamId, "일대호", "1루수");
        playerDao.createPlayer(teamId, "이대호", "2루수");
        playerDao.createPlayer(teamId, "삼대호", "3루수");

        // When
        List<Player> players = playerService.getPlayersByTeamId(teamId);

        // Then
        assertThat(players.size()).isEqualTo(3);
        assertThat(players.get(0).getName()).isEqualTo("일대호");
        assertThat(players.get(1).getName()).isEqualTo("이대호");
        assertThat(players.get(2).getName()).isEqualTo("삼대호");
    }

    @Test
    @DisplayName("Service - 없는 팀 조회")
    void getPlayersByTeamIdFailTestWithInvalidTeamId() {
        // Given
        int teamId = -1;

        // When
        List<Player> players = playerService.getPlayersByTeamId(teamId);

        // Then
        assertThat(players.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Service - 포지션 별 팀 야구선수 페이지 조회 성공")
    void getPlayersPerPositionTest() {
        // Given : 현재 DB에 롯데(1), NC(2) 등록되어 있는 상태
        String firstPosition = "1루수";
        String secondPosition = "2루수";
        playerDao.createPlayer(1, "선수A", firstPosition);
        playerDao.createPlayer(1, "선수B", secondPosition);
        playerDao.createPlayer(2, "선수C", firstPosition);
        playerDao.createPlayer(2, "선수D", secondPosition);

        // When
        PositionRespDto positionRespDto = playerService.getPlayersPerPosition();

        // Then
        assertThat(positionRespDto.getTeams().size()).isEqualTo(2);
        assertThat(positionRespDto.getPositionAndPlayerNames().get(firstPosition).size()).isEqualTo(2);
        assertThat(positionRespDto.getPositionAndPlayerNames().get(secondPosition).size()).isEqualTo(2);

        String firstPlayerNameOfFirstPosition = positionRespDto.getPositionAndPlayerNames().get(firstPosition).get(0);
        assertThat(firstPlayerNameOfFirstPosition).isEqualTo("선수A");
        String firstPlayerNameOfSecondPosition = positionRespDto.getPositionAndPlayerNames().get(secondPosition).get(0);
        assertThat(firstPlayerNameOfSecondPosition).isEqualTo("선수B");
    }
}
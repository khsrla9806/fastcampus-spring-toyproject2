package model.player;

import db.DBConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerDaoTest {
    private Connection connection = DBConnection.getConnection();
    private PlayerDao playerDao = new PlayerDao(connection);

    @BeforeEach
    void beforeEach() throws SQLException {
        connection.setAutoCommit(false);
    }

    @AfterEach
    void afterEach() throws SQLException {
        connection.rollback();
        connection.commit();
        connection.setAutoCommit(true);
        // Auto_Increment 초기화
        PreparedStatement statement = connection.prepareStatement("alter table player auto_increment=?");
        statement.setInt(1, 1);
        statement.execute();
    }

    @Test
    @DisplayName("선수 등록 성공")
    void createPlayerSuccessTest() {
        // Given : 역할 분리를 해서 team 1번은 DB에서 넣어놨습니다.
        // TODO: Team 도메인 완성 후 Team 객체로 테스트 완성

        // When
        int result = playerDao.createPlayer(1, "김훈섭", "1루수");;

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("선수 등록 실패 - 같은 팀에 중복 포지션 존재")
    void createPlayerFailTestWithDuplicatePosition() {
        // Given : 역할 분리를 해서 team 1번은 DB에서 넣어놨습니다.
        // TODO: Team 도메인 완성 후 Team 객체로 테스트 완성
        playerDao.createPlayer(1, "김훈섭", "1루수");

        // When
        int result = playerDao.createPlayer(1, "이대호", "1루수");

        // Then
        assertThat(result).isEqualTo(-1);
    }

    @Test
    @DisplayName("선수 등록 실패 - 존재하지 않는 teamId 사용")
    void createPlayerFailTestWithInvalidTeamId() {
        // Given
        int teamId = 1000;

        // When
        int result = playerDao.createPlayer(teamId, "김훈섭", "1루수");

        // Then
        assertThat(result).isEqualTo(-1);
    }

    @Test
    @DisplayName("선수 조회 테스트")
    void getPlayerByIdTest() {
        // Given
        playerDao.createPlayer(1, "김훈섭", "1루수");
        int playerId = 1;

        // When
        Player player = playerDao.getPlayerById(playerId);

        // Then
        assertThat(player).isNotNull();
        assertThat(player.getId()).isEqualTo(playerId);
        assertThat(player.getName()).isEqualTo("김훈섭");
        assertThat(player.getPosition()).isEqualTo("1루수");
    }

    @Test
    @DisplayName("팀별 선수 조회 테스트")
    void getPlayersTest() {
        // Given
        int teamId = 1;
        playerDao.createPlayer(teamId, "일대호", "1루수");
        playerDao.createPlayer(teamId, "이대호", "2루수");
        playerDao.createPlayer(teamId, "삼대호", "3루수");
        playerDao.createPlayer(teamId, "사대호", "포수");

        // When
        List<Player> players = playerDao.getPlayersByTeamId(teamId);

        // Then
        assertThat(players.size()).isEqualTo(4);
        assertThat(players.get(0).getName()).isEqualTo("일대호");
        assertThat(players.get(1).getName()).isEqualTo("이대호");
        assertThat(players.get(2).getName()).isEqualTo("삼대호");
        assertThat(players.get(3).getName()).isEqualTo("사대호");
    }
}
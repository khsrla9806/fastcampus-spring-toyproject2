package model.player;

import db.DBConnection;
import dto.PositionRespDto;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerDaoTest {
    private Connection connection = DBConnection.getConnection();
    private PlayerDao playerDao = new PlayerDao(connection);

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

    @Test
    @DisplayName("선수 퇴출 시 teamId를 null로 변경")
    void convertOutPlayerTest() {
        // Given
        playerDao.createPlayer(1, "일대호", "1루수");
        int playerId = 1;

        // When
        playerDao.convertOutPlayer(playerId);

        // Then
        Player player = playerDao.getPlayerById(playerId);
        assertThat(player).isNotNull();
        assertThat(player.getTeamId()).isNull();
    }

    // TODO: Team, Stadium 완성되면 테스트 코드 변경
    // 트랜잭션 적용 안 시키기 위해서 임시 적용 (MySQL 더미 데이터 사용)
    @Test
    void getAllPlayersPerPositionTest() {
        PositionRespDto positionRespDto = playerDao.getAllPlayersPerPosition();

        // Table Head
        StringBuilder builder = new StringBuilder(formatData("포지션"));
        positionRespDto.getTeams().forEach(teamName -> builder.append(formatData(teamName)));
        builder.append("\n");

        // Table Data
        for (String position : PositionRespDto.POSITIONS) {
            List<String> players = positionRespDto.getPositionAndPlayerNames().get(position);
            if (players.size() == 0) {
                continue;
            }
            builder.append(formatData(position));
            players.forEach(playerName -> {
                String name = playerName == null ? formatData("") : formatData(playerName);
                builder.append(name);
            });
            builder.append("\n");
        }

        System.out.println(builder);
    }

    private String formatData(String data) {
        return String.format("%-8s", data);
    }
}
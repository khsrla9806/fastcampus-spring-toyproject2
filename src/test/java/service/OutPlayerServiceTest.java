package service;

import db.DBConnection;
import model.dto.OutPlayerRespDto;
import model.outplayer.OutPlayerDao;
import model.player.Player;
import model.player.PlayerDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OutPlayerServiceTest {

    private Connection connection = DBConnection.getConnection();
    private PlayerDao playerDao = new PlayerDao(connection);
    private OutPlayerDao outPlayerDao = new OutPlayerDao(connection);
    private OutPlayerService outPlayerService = new OutPlayerService(playerDao, outPlayerDao);

    @BeforeEach
    void beforeEach() throws SQLException {
        dbInit();
    }

    @AfterEach
    void afterEach() throws SQLException {
        dbInit();
    }

    private void dbInit() throws SQLException {
        connection.prepareStatement("DELETE FROM out_player").execute();
        connection.prepareStatement("DELETE FROM player").execute();
        connection.prepareStatement("alter table out_player auto_increment=1").execute();
        connection.prepareStatement("alter table player auto_increment=1").execute();
    }

    @Test
    @DisplayName("Service - 퇴출 선수 등록 성공")
    void createOutPlayerSuccessTest() {
        // Given
        playerDao.createPlayer(1, "일대호", "1루수");
        int playerId = 1;
        String reason = "집안일";

        // When
        String result = outPlayerService.createOutPlayer(playerId, reason);

        // Then
        assertThat(result).isEqualTo("성공");

        Player player = playerDao.getPlayerById(playerId);
        assertThat(player.getTeamId()).isNull();

        List<OutPlayerRespDto> outPlayers = outPlayerDao.getOutPlayers();
        assertThat(outPlayers.get(0).getPlayerId()).isEqualTo(playerId);
        assertThat(outPlayers.get(0).getOutReason()).isEqualTo(reason);
    }

    @Test
    @DisplayName("Service - 퇴출 선수 등록 실패(존재하지 않는 선수)")
    void createOutPlayerFailTest() {
        // Given
        int playerId = 1;
        String reason = "집안일";

        // When
        String result = outPlayerService.createOutPlayer(playerId, reason);

        // Then
        assertThat(result).isEqualTo("실패");
    }

    @Test
    @DisplayName("Service - 퇴출 선수 목록 조회 성공")
    void getOutPlayersSuccessTest() {
        // Given
        playerDao.createPlayer(1, "일대호", "1루수");
        playerDao.createPlayer(1, "이대호", "2루수");
        playerDao.createPlayer(1, "삼대호", "3루수");

        int playerId = 1; // 일대호
        String reason = "도박";
        outPlayerService.createOutPlayer(playerId, reason);

        // When
        List<OutPlayerRespDto> outPlayers = outPlayerService.getOutPlayers();

        // Then
        assertThat(outPlayers.size()).isEqualTo(3); // 일단 모든 선수가 나오기 떄문에 사이즈는 3

        String outReasonOfOutPlayer = outPlayers.get(0).getOutReason(); // 일대호의 퇴출 사유
        assertThat(outReasonOfOutPlayer).isEqualTo(reason);

        String outReasonOfNoneOutPlayer = outPlayers.get(1).getOutReason(); // 이대호는 퇴출되지 않음
        assertThat(outReasonOfNoneOutPlayer).isNull();
    }

}
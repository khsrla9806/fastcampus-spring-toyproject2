package model.outplayer;

import db.DBConnection;
import model.dto.OutPlayerRespDto;
import model.player.PlayerDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OutPlayerDaoTest {
    private Connection connection = DBConnection.getConnection();
    private OutPlayerDao outPlayerDao = new OutPlayerDao(connection);
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
        connection.prepareStatement("alter table player auto_increment=1").execute();
        connection.prepareStatement("alter table out_player auto_increment=1").execute();
    }

    @Test
    @DisplayName("퇴출 선수 등록 테스트")
    void createOutPlayerTest() {
        // Given
        playerDao.createPlayer(1, "일대호", "1루수");
        int playerId = 1;

        // When
        int result = outPlayerDao.createOutPlayer(playerId, "도박");

        // Then
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("퇴출 선수 목록 조회")
    void getAllOutPlayers() {
        // Given
        playerDao.createPlayer(1, "일대호", "1루수");
        playerDao.createPlayer(1, "이대호", "2루수");
        playerDao.createPlayer(1, "삼대호", "3루수");

        outPlayerDao.createOutPlayer(2, "도박"); // 이대호 퇴출
        outPlayerDao.createOutPlayer(3, "집안일"); // 삼대호 퇴출

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY.MM.dd");
        String todayDate = now.format(dateTimeFormatter);

        // When
        List<OutPlayerRespDto> outAllPlayers = outPlayerDao.getOutAllPlayers();

        // Then
        assertThat(outAllPlayers.size()).isEqualTo(3);
        assertThat(outAllPlayers.get(1).getOutReason()).isEqualTo("도박");
        assertThat(outAllPlayers.get(2).getOutReason()).isEqualTo("집안일");
        assertThat(outAllPlayers.get(2).getOutDate()).isEqualTo(todayDate);
    }

}
package db;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

class DBConnectionTest {
    @Test
    @DisplayName("DB Connection 테스트")
    void DBConnectionTest() {
        // Given & When
        Connection connection = DBConnection.getConnection();

        // Then
        assertThat(connection).isNotNull();
    }
}
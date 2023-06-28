package model.stadium;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StadiumDao {
    private Connection connection;

    public StadiumDao(Connection connection) {
        this.connection = connection;
    }
    //야구장 등록
    public int createStadium(String name) throws SQLException{
        String query = "INSERT INTO stadium (id, name, created_at) VALUES(?,?,now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);

            return statement.executeUpdate();
            }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    //전체 야구장 목록 보기
    public List<Stadium> getAllStadiums() {
        List<Stadium> stadiums = new ArrayList<>();
        String query = "SELECT * FROM stadium";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    stadiums.add(getStadiumFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return stadiums;
    }
    private Stadium getStadiumFromResultSet(ResultSet resultSet) {
        try {
            Integer id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Timestamp createdAt = resultSet.getTimestamp("created_at");

            return Stadium.builder()
                    .id(id)
                    .name(name)
                    .createdAt(createdAt)
                    .build();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

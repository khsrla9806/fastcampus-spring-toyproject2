package service;

import db.DBConnection;
import model.stadium.Stadium;
import model.stadium.StadiumDao;
import service.StadiumService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class StadiumServiceTest {
    public static void main(String[] args) {
        try {
            Connection connection = DBConnection.getConnection();
            StadiumDao stadiumDao = new StadiumDao(connection);
            StadiumService stadiumService = new StadiumService(stadiumDao);

            // 테스트 createStadium 메소드
            String stadiumName = "Example Stadium";
            String result = stadiumService.createStadium(stadiumName);
            System.out.println(result);

            // 테스트 getAllStadiums 메소드
            List<Stadium> stadiums = stadiumService.getAllStadiums();
            for (Stadium stadium : stadiums) {
                System.out.println(stadium);
            }

            // DB 연결 닫기
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

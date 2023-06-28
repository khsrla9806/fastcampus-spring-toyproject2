package service;

import model.stadium.Stadium;
import service.StadiumService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class StadiumServiceTest {
    public static void main(String[] args) {
        try {
            // 데이터베이스 연결 설정
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/baseball", "root", "root1234");

            // 스타디움서비스 인스턴스 생성
            StadiumService stadiumService = new StadiumService(connection);

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

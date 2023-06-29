package model.team;

import dto.TeamRespDto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class TeamDaoTest {

    public static void main(String[] args) {
        // 데이터베이스 연결 설정
        String url = "jdbc:mysql://localhost:3306/baseball";
        String username = "root";
        String password = "root1234";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            TeamDao teamDao = new TeamDao(connection);

            // 팀 등록
            int teamId = 1;
            int stadiumId = 1;
            String teamName = "Example Team";
            int rowsAffected = teamDao.createTeam( stadiumId, teamName);
            if (rowsAffected > 0) {
                System.out.println("Team created successfully.");
            } else {
                System.out.println("Failed to create team.");
            }

            // 전체 팀 목록 가져오기
            List<TeamRespDto> teams = teamDao.getAllTeams();
            if (teams != null) {
                System.out.println("All teams:");
                for (TeamRespDto team : teams) {
                    System.out.println("Team ID: " + team.getId());
                    System.out.println("Team Name: " + team.getTeamName());
                    System.out.println("Stadium ID: " + team.getStadiumId());
                    System.out.println("Stadium Name: " + team.getStadiumName());
                    System.out.println();
                }
            } else {
                System.out.println("Failed to retrieve teams.");
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

}

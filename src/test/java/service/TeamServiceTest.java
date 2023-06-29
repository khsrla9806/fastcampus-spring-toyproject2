package service;

import db.DBConnection;
import dto.TeamRespDto;
import model.team.TeamDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class TeamServiceTest {
    public static void main(String[] args) {
        try {
            Connection connection = DBConnection.getConnection();
            TeamDao teamDao = new TeamDao(connection);
            TeamService teamService = new TeamService(teamDao);

            // 테스트 팀생성 메소드
            int stadiumId = 1; // Provide the stadium ID
            String teamName = "Example Team";
            String result = teamService.createTeam(stadiumId, teamName);
            System.out.println(result);

            // 테스트 getAllTeams 메소드
            List<TeamRespDto> teams = teamService.getAllTeams();
            for (TeamRespDto team : teams) {
                System.out.println("Team ID: " + team.getId());
                System.out.println("Team Name: " + team.getTeamName());
                System.out.println("Stadium ID: " + team.getStadiumId());
                System.out.println("Stadium Name: " + team.getStadiumName());
                System.out.println("-------------------------");
            }

            // DB 연결 닫기
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


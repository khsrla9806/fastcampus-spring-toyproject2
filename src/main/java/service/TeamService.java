package service;

import model.dto.TeamRespDto;
import model.team.Team;
import model.team.TeamDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TeamService {
    private TeamDao teamDao;

    public TeamService(Connection connection) {
        this.teamDao = new TeamDao(connection);
    }

    // 팀 등록 기능
    public String createTeam(int stadiumId, String name) {
        try {
            int result = teamDao.createTeam(stadiumId, name);
            if (result > 0) {
                return "성공";
            } else {
                return "실패";
            }
        } catch (SQLException e) {
            System.out.println("Failed to create team: " + e.getMessage());
            return "실패";
        }
    }

    // 전체 팀 목록 조회
    public List<TeamRespDto> getAllTeams() {
        List<TeamRespDto> teams = teamDao.getAllTeams();
        if (!teams.isEmpty()) {
            return teams;
        } else {
            return List.of();
        }
    }
}

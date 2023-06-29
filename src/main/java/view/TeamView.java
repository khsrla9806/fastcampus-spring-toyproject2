package view;

import dto.TeamRespDto;
import service.TeamService;

import java.util.List;
import java.util.Map;

public class TeamView {
    private final TeamService teamService;

    public TeamView(TeamService teamService) {
        this.teamService = teamService;
    }

    public void create(Map<String, Object> params) {
        try {
            int stadiumId = Integer.parseInt(params.get("stadiumId").toString());
            String name = params.get("name").toString();
            System.out.println(teamService.createTeam(stadiumId, name));
        } catch (Exception e) {
            throw new RuntimeException("잘못된 요청입니다.");
        }

    }

    public void findTeams() {
        try {
            List<TeamRespDto> teams = teamService.getAllTeams();
            String[] tableHead = {"팀 ID", "팀명", "스타디움 ID", "스타디움명"};
            printRow(tableHead);
            for (TeamRespDto team : teams) {
                String[] tableData = {
                        team.getId().toString(),
                        team.getTeamName(),
                        team.getStadiumId().toString(),
                        team.getStadiumName()
                };
                printRow(tableData);
            }
        } catch (Exception e) {
            throw new RuntimeException("잘못된 요청입니다.");
        }

    }
    private void printRow(String[] input) {
        StringBuilder builder = new StringBuilder();
        for (String str : input) {
            builder.append(ViewFormatter.getFormatData(str));
        }
        System.out.println(builder);
    }
}

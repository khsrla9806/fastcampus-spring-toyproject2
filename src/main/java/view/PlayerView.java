package view;

import dto.PositionRespDto;
import model.player.Player;
import service.PlayerService;

import java.util.List;
import java.util.Map;

public class PlayerView {
    private final PlayerService playerService;

    public PlayerView(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void create(Map<String, Object> params) {
        try {
            int teamId = Integer.parseInt(params.get("teamId").toString());
            String name = params.get("name").toString();
            String position = params.get("position").toString();
            System.out.println(playerService.createPlayer(teamId, name, position));
        } catch (Exception e) {
            throw new RuntimeException("잘못된 요청입니다.");
        }
    }

    public void findPlayers(Map<String, Object> params) {
        try {
            int teamId = Integer.parseInt(params.get("teamId").toString());
            List<Player> players = playerService.getPlayersByTeamId(teamId);
            String[] tableHead = {"선수Id" ,"이름", "포지션", "등록일"};
            printRow(tableHead);
            for (Player player : players) {
                String[] tableData = {
                        player.getId().toString(),
                        player.getName(),
                        player.getPosition(),
                        ViewFormatter.getFormatDateTime(player.getCreatedAt())
                };
                printRow(tableData);
            }
        } catch (Exception e) {
            throw new RuntimeException("잘못된 요청입니다.");
        }
    }

    public void findPlayersByPosition() {
        PositionRespDto playersPerPosition = playerService.getPlayersPerPosition();
        String[] tableHead = new String[playersPerPosition.getTeams().size() + 1];
        tableHead[0] = "포지션";
        for (int i = 1; i < tableHead.length; i++) {
            tableHead[i] = playersPerPosition.getTeams().get(i - 1);
        }
        printRow(tableHead);
        for (String position : PositionRespDto.POSITIONS) {
            List<String> playerNames = playersPerPosition.getPositionAndPlayerNames().get(position);
            if (!playerNames.isEmpty()) {
                String[] tableData = new String[playerNames.size() + 1];
                tableData[0] = position;
                for (int i = 1; i <= playerNames.size(); i++) {
                    tableData[i] = playerNames.get(i - 1);
                }
                printRow(tableData);
            }
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

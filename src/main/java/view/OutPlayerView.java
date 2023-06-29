package view;

import dto.OutPlayerRespDto;
import service.OutPlayerService;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class OutPlayerView {

    private final OutPlayerService outPlayerService;

    public OutPlayerView(OutPlayerService outPlayerService) {
        this.outPlayerService = outPlayerService;
    }

    public void create(Map<String, Object> params) {
        try {
            int playerId = Integer.parseInt(params.get("playerId").toString());
            String reason = params.get("reason").toString();
            System.out.println(outPlayerService.createOutPlayer(playerId, reason));
        } catch (Exception e) {
            throw new RuntimeException("잘못된 요청입니다.");
        }
    }

    public void findOutPlayers() {
        List<OutPlayerRespDto> outPlayers = outPlayerService.getOutPlayers();
        String[] tableHead = {"선수Id" ,"이름", "포지션", "퇴출사유", "퇴출일"};
        printRow(tableHead);
        for (OutPlayerRespDto player : outPlayers) {
            String[] tableData = {
                    player.getPlayerId().toString(),
                    player.getPlayerName(),
                    player.getPlayerPosition(),
                    player.getOutReason(),
                    getFormatDate(player.getOutDate())
            };
            printRow(tableData);
        }
    }

    private void printRow(String[] input) {
        StringBuilder builder = new StringBuilder();
        for (String str : input) {
            builder.append(formatData(str));
        }
        System.out.println(builder);
    }

    private String formatData(String data) {
        if (data == null) {
            return "";
        }
        return String.format("%-8s", data);
    }

    private String getFormatDate(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY.MM.dd");
        return timestamp.toLocalDateTime().format(formatter);
    }
}

package dto;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
@Getter
public class PositionRespDto {
    public static final String[] POSITIONS = {"1루수", "2루수", "3루수", "포수", "투수", "유격수", "좌익수", "중견수", "우익수"};
    private final List<String> teams;
    private final Map<String, List<String>> positionAndPlayerNames;

    public PositionRespDto() {
        this.teams = new ArrayList<>();
        this.positionAndPlayerNames = new HashMap<>();
        for (String position : POSITIONS) {
            positionAndPlayerNames.put(position, new ArrayList<>());
        }
    }

    // TODO: Team 도메인 완성되면 아마도 List<TeamRespDto> -> 이름만 뽑아내서 사용 (순서보장!)
    public void setTeams(List<String> teams) {
        this.teams.addAll(teams);
    }

    public void setPositionAndPlayerName(String position, String playerName) {
        positionAndPlayerNames.get(position).add(playerName);
    }
}

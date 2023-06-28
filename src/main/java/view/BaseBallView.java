package view;

import config.AppConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static view.CommandConst.*;

public class BaseBallView {
    private final AppConfig appConfig;
    private final PlayerView playerView;
    private final OutPlayerView outPlayerView;
    private final StadiumView stadiumView;
    private final TeamView teamView;

    public BaseBallView() {
        this.appConfig = AppConfig.getInstance();
        this.playerView = new PlayerView(appConfig.getPlayerService());
        this.outPlayerView = new OutPlayerView(appConfig.getOutPlayerService());
        this.stadiumView = new StadiumView(appConfig.getStadiumService());
        this.teamView = new TeamView(appConfig.getTeamService());
    }

    public void renderWithParams(String command, Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            throw new RuntimeException("잘못된 요청입니다.");
        }
        switch (command) {
            case CREATE_STADIUM:
                System.out.println("야구장등록 요청");
                stadiumView.create(params);
                return;
            case CREATE_TEAM:
                System.out.println("팀등록 요청");
                teamView.create(params);
                return;
            case CREATE_PLAYER:
                System.out.println("선수등록 요청");
                playerView.create(params);
                return;
            case PLAYER_LIST:
                System.out.println("선수목록 요청");
                playerView.findPlayers(params);
                return;
            case CREATE_OUT_PLAYER:
                System.out.println("퇴출등록 요청");
                outPlayerView.create(params);
                return;
            default:
                System.out.println("잘못된 요청입니다.");
        }
        System.out.println();
    }

    public void renderWithoutParams(String command) {
        switch (command) {
            case STADIUM_LIST:
                System.out.println("야구장목록 요청");
                stadiumView.findStadiums();
                return;
            case TEAM_LIST:
                System.out.println("팀목록 요청");
                teamView.findTeams();
                return;
            case OUT_PLAYER_LIST:
                System.out.println("퇴출목록 요청");
                outPlayerView.findOutPlayers();
                return;
            case PLAYER_LIST_BY_POSITION:
                System.out.println("포지션별목록 요청");
                playerView.findPlayersByPosition();
                return;
            default:
                System.out.println("잘못된 요청입니다.");
        }
    }

    public String[] separateCommandAndParams(String input) {
        return input.split("\\?");
    }

    public Map<String, Object> getParams(String[] commandAndParams) {
        Map<String, Object> params = new HashMap<>();
        StringTokenizer paramsToken = new StringTokenizer(commandAndParams[1], "&");

        while (paramsToken.hasMoreTokens()) {
            StringTokenizer keyAndValueToken = new StringTokenizer(paramsToken.nextToken(), "=");
            if (keyAndValueToken.countTokens() == 2) {
                String key = keyAndValueToken.nextToken();
                Object value = keyAndValueToken.nextToken();
                params.put(key, value);
            } else {
                throw new RuntimeException("잘못된 요청입니다.");
            }
        }

        return params;
    }
}

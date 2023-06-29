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
                stadiumView.create(params);
                return;
            case CREATE_TEAM:
                teamView.create(params);
                return;
            case CREATE_PLAYER:
                playerView.create(params);
                return;
            case PLAYER_LIST:
                playerView.findPlayers(params);
                return;
            case CREATE_OUT_PLAYER:
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
                stadiumView.findStadiums();
                return;
            case TEAM_LIST:
                teamView.findTeams();
                return;
            case OUT_PLAYER_LIST:
                outPlayerView.findOutPlayers();
                return;
            case PLAYER_LIST_BY_POSITION:
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

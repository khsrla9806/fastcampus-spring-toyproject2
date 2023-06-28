package config;

import db.DBConnection;
import lombok.Getter;
import model.outplayer.OutPlayerDao;
import model.player.PlayerDao;
import model.stadium.StadiumDao;
import model.team.TeamDao;
import service.OutPlayerService;
import service.PlayerService;
import service.StadiumService;
import service.TeamService;

import java.sql.Connection;

@Getter
public class AppConfig {
    private final static AppConfig instance = new AppConfig();
    private final Connection connection;
    private final PlayerDao playerDao;
    private final PlayerService playerService;
    private final OutPlayerDao outPlayerDao;
    private final OutPlayerService outPlayerService;
    private final TeamDao teamDao;
    private final TeamService teamService;
    private final StadiumDao stadiumDao;
    private final StadiumService stadiumService;

    public static AppConfig getInstance() {
        return instance;
    }

    private AppConfig() {
        this.connection = DBConnection.getConnection();
        this.playerDao = new PlayerDao(connection);
        this.playerService = new PlayerService(playerDao);
        this.outPlayerDao = new OutPlayerDao(connection);
        this.outPlayerService = new OutPlayerService(playerDao, outPlayerDao);
        this.stadiumDao = new StadiumDao(connection);
        this.stadiumService = new StadiumService(stadiumDao);
        this.teamDao = new TeamDao(connection);
        this.teamService = new TeamService(teamDao);
    }
}

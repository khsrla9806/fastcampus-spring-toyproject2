package config;

import db.DBConnection;
import lombok.Getter;
import model.outplayer.OutPlayerDao;
import model.player.PlayerDao;
import service.OutPlayerService;
import service.PlayerService;

import java.sql.Connection;

@Getter
public class AppConfig {
    private final static AppConfig instance = new AppConfig();
    private final Connection connection;
    private final PlayerDao playerDao;
    private final PlayerService playerService;
    private final OutPlayerDao outPlayerDao;
    private final OutPlayerService outPlayerService;

    public static AppConfig getInstance() {
        return instance;
    }

    private AppConfig() {
        this.connection = DBConnection.getConnection();
        this.playerDao = new PlayerDao(connection);
        this.playerService = new PlayerService(playerDao);
        this.outPlayerDao = new OutPlayerDao(connection);
        this.outPlayerService = new OutPlayerService(playerDao, outPlayerDao);
    }
}

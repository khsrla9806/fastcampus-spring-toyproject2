package view;

import service.PlayerService;

public class PlayerView {
    private final PlayerService playerService;

    public PlayerView(PlayerService playerService) {
        this.playerService = playerService;
    }
}

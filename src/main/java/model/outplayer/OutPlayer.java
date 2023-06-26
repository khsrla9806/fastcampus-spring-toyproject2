package model.outplayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@ToString
@Builder
@AllArgsConstructor
@Getter
public class OutPlayer {
    private Integer id; // Primary Key
    private Integer playerId; // Player(1) : OutPlayer(1)
    private String reason;
    private Timestamp createdAt;
}

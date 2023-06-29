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
    private Integer id;
    private Integer playerId;
    private String reason;
    private Timestamp createdAt;
}

package model.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@ToString
@Builder
@AllArgsConstructor
@Getter
public class Player {
    private Integer id; // Primary Key
    private Integer teamId; // Team(1) : Player(N)
    private String name;
    private String position;
    private Timestamp createdAt;
}

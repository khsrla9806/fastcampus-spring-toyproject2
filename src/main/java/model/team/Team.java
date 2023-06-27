package model.team;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
@Getter
@ToString
public class Team {
    private Integer id; // Primary Key
    private Integer stadiumId; // Team(1) : Stadium(1)
    private String name;
    private Timestamp createdAt;

    @Builder
    public Team(int id, int stadiumId, String name, Timestamp createdAt){
        this.id = id;
        this.stadiumId = stadiumId;
        this.name = name;
        this.createdAt = createdAt;
    }
}

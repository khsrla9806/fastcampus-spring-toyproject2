package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;



@AllArgsConstructor
@ToString
@Builder
@Getter
public class TeamRespDto {
    Integer id;
    Integer stadiumId;
    String teamName;
    String stadiumName;
}

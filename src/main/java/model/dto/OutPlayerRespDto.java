package model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@ToString
@Builder
@AllArgsConstructor
@Getter
public class OutPlayerRespDto {
    Integer playerId;
    String playerName;
    String playerPosition;
    String outReason;
    Timestamp outDate;
}

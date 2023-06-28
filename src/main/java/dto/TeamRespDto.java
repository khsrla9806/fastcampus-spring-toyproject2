package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;


@AllArgsConstructor
@ToString
@Builder
@Getter
public class TeamRespDto {
    Integer id; // Primary Key
    Integer stadiumId; //
    String name;
    Date createdAt;
}

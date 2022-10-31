package inhatc.capstone.baro.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class PersonalRankingDto {
	private Long id;
	private String nickname;
	private Long point;
	private String university;
}

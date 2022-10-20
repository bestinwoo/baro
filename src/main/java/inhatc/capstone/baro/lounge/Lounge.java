package inhatc.capstone.baro.lounge;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import inhatc.capstone.baro.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class Lounge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private LocalDateTime createDate;

	public static Lounge writeLounge(LoungeDto.Request request) {
		return Lounge.builder()
			.content(request.getContent())
			.member(Member.builder().id(request.getMemberId()).build())
			.createDate(LocalDateTime.now())
			.build();
	}
}

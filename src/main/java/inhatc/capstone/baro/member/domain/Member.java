package inhatc.capstone.baro.member.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import inhatc.capstone.baro.oauth2.OAuth2Provider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String oauth2Id;
	private String email;
	private String nickname;
	private String userProfileImage;
	private boolean isFirst;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_id")
	private Job job;
	private String university;
	private String introduce;
	private Long point;

	@Enumerated(EnumType.STRING)
	private OAuth2Provider provider;

	public static Member createMember(String oauth2Id, String email, OAuth2Provider provider) {
		Member member = new Member();
		member.oauth2Id = oauth2Id;
		member.email = email;
		member.provider = provider;
		member.nickname = "anonymous";
		member.point = 0L;
		member.isFirst = true;
		return member;
	}

}

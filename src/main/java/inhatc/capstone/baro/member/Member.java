package inhatc.capstone.baro.member;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import inhatc.capstone.baro.oauth2.OAuth2Provider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	@Enumerated(EnumType.STRING)
	private OAuth2Provider provider;

	public static Member createMember(String oauth2Id, String email, OAuth2Provider provider) {
		Member member = new Member();
		member.oauth2Id = oauth2Id;
		member.email = email;
		member.provider = provider;
		member.isFirst = true;
		return member;
	}

}

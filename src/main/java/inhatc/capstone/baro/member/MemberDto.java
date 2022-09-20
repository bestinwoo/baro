package inhatc.capstone.baro.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
	private Long id;
	private String oauth2Id;
	private String email;
	private String nickname;
	private String userProfileImage;
}

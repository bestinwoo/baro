package inhatc.capstone.baro.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberDto {
	private Long id;
	private String oauth2Id;
	private String email;
	private String nickname;
	private String userProfileImage;
}

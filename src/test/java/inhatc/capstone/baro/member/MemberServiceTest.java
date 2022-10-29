package inhatc.capstone.baro.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import inhatc.capstone.baro.member.dto.MemberDto;
import inhatc.capstone.baro.ranking.SchoolRepository;

@SpringBootTest
@Transactional
class MemberServiceTest {
	@Autowired
	MemberService memberService;
	@Autowired
	SchoolRepository schoolRepository;

	@Test
	@DisplayName("회원가입 시 등록되지 않은 대학교는 새로 등록되어야 한다.")
	void join() {
		MemberDto.Register register = new MemberDto.Register();
		register.setId(2L);
		register.setJobId(7L);
		register.setJobLevel("고수");
		register.setNickname("테스트");
		register.setUniversity("서울대학교");
		boolean existSchool = schoolRepository.existsByName(register.getUniversity());
		Assertions.assertThat(existSchool).isFalse();
		memberService.join(register);
		existSchool = schoolRepository.existsByName(register.getUniversity());
		Assertions.assertThat(existSchool).isTrue();
	}

}

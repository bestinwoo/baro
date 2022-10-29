package inhatc.capstone.baro.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import inhatc.capstone.baro.ranking.dto.PersonalRankingDto;
import inhatc.capstone.baro.ranking.dto.SchoolRankingDto;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
	@Autowired
	MemberRepository memberRepository;

	@Test
	@DisplayName("개인 랭킹 조회 테스트")
	void findAllByOrderByPointDesc() {
		Page<PersonalRankingDto> personalRank = memberRepository.findAllByOrderByPointDesc(
			PageRequest.of(0, 5));

		Assertions.assertThat(personalRank).isNotEmpty();
	}

	@Test
	@DisplayName("학교 랭킹 조회 테스트")
	void findBySchoolRanking() {
		Page<SchoolRankingDto> schools = memberRepository.findGroupByUniversityOrderByPointDesc(
			PageRequest.of(0, 2));

		Assertions.assertThat(schools.getTotalElements()).isGreaterThan(0L);
	}
}

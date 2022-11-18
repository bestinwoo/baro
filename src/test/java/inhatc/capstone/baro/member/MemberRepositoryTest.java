package inhatc.capstone.baro.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
	@Autowired
	MemberRepository memberRepository;

	// @Test
	// @DisplayName("개인 랭킹 조회 테스트")
	// void findAllByOrderByPointDesc() {
	// 	Page<PersonalRankingDto> personalRank = memberRepository.findAllByOrderByPointDesc(
	// 		PageRequest.of(0, 5));
	//
	// 	Assertions.assertThat(personalRank).isNotEmpty();
	// }
	//
	// @Test
	// @DisplayName("학교 랭킹 조회 테스트")
	// void findBySchoolRanking() {
	// 	Page<SchoolRankingDto> schools = memberRepository.findGroupByUniversityOrderByPointDesc(
	// 		PageRequest.of(0, 2));
	//
	// 	Assertions.assertThat(schools.getTotalElements()).isGreaterThan(0L);
	// }
}

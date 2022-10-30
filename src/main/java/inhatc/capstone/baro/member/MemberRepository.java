package inhatc.capstone.baro.member;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import inhatc.capstone.baro.member.domain.Member;
import inhatc.capstone.baro.oauth2.OAuth2Provider;
import inhatc.capstone.baro.ranking.dto.PersonalRankingDto;
import inhatc.capstone.baro.ranking.dto.SchoolRankingDto;

public interface MemberRepository extends JpaRepository<Member, Long> {
	/**
	 * 이메일로 사용자 계정 단건 조회
	 * @param email 조회할 대상 계정의 이메일
	 * @return Optional 객체 (account 객체 있는 경우), 빈 Optional 객체 (account 가 없는 경우)
	 */
	Optional<Member> findByEmail(String email);

	/**
	 * 이메일과 OAuth2 제공자로 계정 단건 조회
	 * @param email 조회할 대상 계정의 이메일
	 * @param provider 조회할 대상 계정의 OAuth2 제공자 정보
	 * @return Optional 객체 (account 객체 있는 경우), 빈 Optional 객체 (account 가 없는 경우)
	 */

	Optional<Member> findByEmailAndProvider(String email, OAuth2Provider provider);

	/**
	 * ID로 회원가입 완료된 회원 조회
	 * @param id 조회할 회원의 ID
	 * @return Optional Member
	 */
	Optional<Member> findByIdAndIsFirstIsFalse(Long id);

	boolean existsByNickname(String nickname);

	/**
	 * 개인 랭킹 조회
	 * @param pageable
	 * @return 랭킹순으로 멤버 정렬하여 반환
	 */
	Page<PersonalRankingDto> findAllByOrderByPointDesc(Pageable pageable);

	@Query(value =
		"select m.university as university, sum(m.point) as point, count(m.university) as personnel "
			+ "from Member m "
			+ "where m.isFirst = false "
			+ "group by m.university "
			+ "order by point desc")
	Page<SchoolRankingDto> findGroupByUniversityOrderByPointDesc(Pageable pageable);

}

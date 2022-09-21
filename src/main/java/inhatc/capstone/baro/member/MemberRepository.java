package inhatc.capstone.baro.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import inhatc.capstone.baro.oauth2.OAuth2Provider;

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

	boolean existsByNickname(String nickname);
}

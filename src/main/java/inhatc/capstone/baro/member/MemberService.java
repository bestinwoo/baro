package inhatc.capstone.baro.member;

import static inhatc.capstone.baro.exception.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inhatc.capstone.baro.exception.CustomException;
import inhatc.capstone.baro.member.domain.Member;
import inhatc.capstone.baro.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class MemberService {
	private final MemberRepository memberRepository;

	/**
	 * 회원 가입
	 * @param register 회원 가입에 필요한 정보 (닉네임, 직무, 대학교)
	 */
	public void join(MemberDto.Register register) {
		Member member = memberRepository.findById(register.getId()).orElseThrow(() -> new CustomException(INVALID_ID));
		if (!member.isFirst()) { // 이미 가입된 유저일경우 exception throw
			throw new CustomException(EXIST_MEMBER);
		}
		member.registerMember(register);
	}

	/**
	 * 회원정보 조회
	 * @param memberId 조회할 대상 회원 ID
	 * @return 회원정보
	 */
	public MemberDto.Info getMemberInfo(Long memberId) {
		Member member = memberRepository.findByIdAndIsFirstIsFalse(memberId)
			.orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));

		return MemberDto.Info.from(member);
	}

	public MemberDto.Info modifyMemberInfo(Long memberId) {

	}
}

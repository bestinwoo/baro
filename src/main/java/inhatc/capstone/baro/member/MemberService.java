package inhatc.capstone.baro.member;

import static inhatc.capstone.baro.exception.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inhatc.capstone.baro.exception.CustomException;
import inhatc.capstone.baro.member.domain.Member;
import inhatc.capstone.baro.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memberRepository;

	@Transactional(rollbackFor = Exception.class)
	public void join(MemberDto.Register register) {
		Member member = memberRepository.findById(register.getId()).orElseThrow(() -> new CustomException(INVALID_ID));
		if (!member.isFirst()) { // 이미 가입된 유저일경우 exception throw
			throw new CustomException(EXIST_MEMBER);
		}
		member.registerMember(register);
	}
}

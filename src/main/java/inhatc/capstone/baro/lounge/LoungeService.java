package inhatc.capstone.baro.lounge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import inhatc.capstone.baro.exception.CustomException;
import inhatc.capstone.baro.exception.ErrorCode;
import inhatc.capstone.baro.member.MemberRepository;
import inhatc.capstone.baro.member.domain.Member;
import inhatc.capstone.baro.project.dto.PointType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class LoungeService {
	private final LoungeRepository loungeRepository;
	private final MemberRepository memberRepository;

	//라운지 글 작성
	public LoungeDto.Response writeLounge(@Validated @RequestBody LoungeDto.Request request) {
		Lounge lounge = Lounge.writeLounge(request);
		Member member = memberRepository.findById(request.getMemberId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
		member.addPoint(PointType.WRITE_IDEA);
		lounge = loungeRepository.save(lounge);
		return LoungeDto.Response.from(lounge);
	}

	//라운지 글 조회 (페이징)
	public Page<LoungeDto.Response> getLoungeList(Pageable pageable) {
		Page<Lounge> lounges = loungeRepository.findAllByOrderByCreateDateDesc(pageable);
		return lounges.map(LoungeDto.Response::from);
	}
}

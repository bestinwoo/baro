package inhatc.capstone.baro.ranking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inhatc.capstone.baro.member.MemberRepository;
import inhatc.capstone.baro.ranking.dto.PersonalRankingDto;
import inhatc.capstone.baro.ranking.dto.SchoolRankingDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingService {
	private final MemberRepository memberRepository;

	//개인 랭킹
	public Page<PersonalRankingDto> getPersonalRanking(Pageable pageable) {
		return memberRepository.findAllByOrderByPointDesc(pageable);
	}

	//학교 랭킹
	public Page<SchoolRankingDto> getSchoolRanking(Pageable pageable) {
		return memberRepository.findGroupByUniversityOrderByPointDesc(pageable);
	}
}

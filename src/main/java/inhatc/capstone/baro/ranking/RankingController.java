package inhatc.capstone.baro.ranking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inhatc.capstone.baro.ranking.dto.PersonalRankingDto;
import inhatc.capstone.baro.ranking.dto.SchoolRankingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "랭킹 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rank")
public class RankingController {
	private final RankingService rankingService;

	@Operation(summary = "개인 랭킹 조회")
	@GetMapping("/personal")
	public ResponseEntity<Page<PersonalRankingDto>> getPersonalRanking(Pageable pageable) {
		Page<PersonalRankingDto> personalRanking = rankingService.getPersonalRanking(
			PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()));

		return ResponseEntity.ok(personalRanking);
	}

	@Operation(summary = "학교 랭킹 조회")
	@GetMapping("/school")
	public ResponseEntity<Page<SchoolRankingDto>> getSchoolRanking(Pageable pageable) {
		Page<SchoolRankingDto> schoolRanking = rankingService.getSchoolRanking(
			PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()));

		return ResponseEntity.ok(schoolRanking);
	}

}

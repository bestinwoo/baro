package inhatc.capstone.baro.lounge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "라운지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/lounge")
public class LoungeController {
	private final LoungeService loungeService;

	@Operation(summary = "라운지 작성")
	@ApiResponse(responseCode = "201", description = "작성 완료")
	@PostMapping
	public ResponseEntity<LoungeDto.Response> writeLounge(@Validated @RequestBody LoungeDto.Request request) {
		LoungeDto.Response response = loungeService.writeLounge(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "라운지 조회")
	@GetMapping
	public ResponseEntity<Page<LoungeDto.Response>> getLoungeList(@PageableDefault(page = 1) Pageable pageable) {
		Page<LoungeDto.Response> loungeList = loungeService.getLoungeList(
			PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()));
		return ResponseEntity.ok(loungeList);
	}
}

package inhatc.capstone.baro.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
	private final MemberRepository memberRepository;
	@GetMapping("/{email}")
	public ResponseEntity<Member> getMemberInfo(@PathVariable String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(IllegalStateException::new);
		return ResponseEntity.ok(member);
	}
}

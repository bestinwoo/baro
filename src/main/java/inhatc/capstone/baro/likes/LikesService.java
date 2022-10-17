package inhatc.capstone.baro.likes;

import static inhatc.capstone.baro.exception.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inhatc.capstone.baro.exception.CustomException;
import inhatc.capstone.baro.exception.ErrorCode;
import inhatc.capstone.baro.jwt.SecurityUtil;
import inhatc.capstone.baro.member.domain.Member;
import inhatc.capstone.baro.project.domain.Project;
import inhatc.capstone.baro.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class LikesService {
	private final LikesRepository likesRepository;
	private final ProjectRepository projectRepository;

	//좋아요
	public void likeProject(LikesDto likesDto) {
		Long memberId = SecurityUtil.getCurrentMemberId();
		if (likesRepository.existsByMemberIdAndProjectId(memberId, likesDto.getProjectId())) {
			throw new CustomException(ALREADY_LIKE);
		}
		Project project = projectRepository.findById(likesDto.getProjectId())
			.orElseThrow(() -> new CustomException(NOT_FOUND_PROJECT));

		Likes like = Likes.builder()
			.member(Member.builder().id(memberId).build())
			.project(project)
			.build();

		like.getProject().increaseLikeCount();

		Likes save = likesRepository.save(like);
	}

	//취소
	public void unlikeProject(LikesDto likesDto) {
		Long memberId = SecurityUtil.getCurrentMemberId();
		Likes like = likesRepository.findByMemberIdAndProjectId(memberId, likesDto.getProjectId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_DID_LIKE));

		like.getProject().decreaseLikeCount();
		likesRepository.delete(like);

	}

}

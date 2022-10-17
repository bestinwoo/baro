package inhatc.capstone.baro.likes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
	Optional<Likes> findByMemberIdAndProjectId(Long memberId, Long projectId);

	boolean existsByMemberIdAndProjectId(Long memberId, Long projectId);
}

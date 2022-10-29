package inhatc.capstone.baro.ranking;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
	boolean existsByName(String name);

	Optional<School> findByName(String name);

}

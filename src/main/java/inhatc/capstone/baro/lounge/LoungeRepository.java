package inhatc.capstone.baro.lounge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoungeRepository extends JpaRepository<Lounge, Long> {
	Page<Lounge> findAllByOrderByCreateDateDesc(Pageable pageable);
}

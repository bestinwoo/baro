package inhatc.capstone.baro.job;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobRepository extends JpaRepository<Job, Long> {
	@Query("select distinct j from Job j left join fetch j.children where j.parent is null")
	List<Job> findByParentIsNull();
}

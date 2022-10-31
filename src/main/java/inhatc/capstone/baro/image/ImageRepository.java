package inhatc.capstone.baro.image;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
	List<Image> findByImagePathIn(Collection<String> imagePaths);
}

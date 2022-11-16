package inhatc.capstone.baro.image;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import inhatc.capstone.baro.exception.CustomException;
import inhatc.capstone.baro.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
	private final ImageRepository imageRepository;

	public String saveImage(MultipartFile multipartFile, String type) throws IOException {
		String originName = multipartFile.getOriginalFilename().toLowerCase();
		if (!originName.endsWith(".png") && !originName.endsWith(".jpg")) {
			throw new CustomException(ErrorCode.INVALID_IMAGE_EXTENSION);
		}
		String absolutePath = new File("").getAbsolutePath() + File.separator;
		String path = "src/main/resources/images/" + type + "/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String fileName = RandomStringUtils.randomAlphabetic(32) + ".png"; //type + "-" + id + ".png";
		String imagePath = path + "/" + fileName;

		file = new File(absolutePath + imagePath);
		multipartFile.transferTo(file);

		Image image = Image.builder().imagePath(fileName).imageOriginPath(originName).build();
		imageRepository.save(image);
		return image.getImagePath();
	}

	public Resource getImageByPath(String fileName, String type) {
		String absolutePath = new File("").getAbsolutePath() + File.separator;
		String path = absolutePath + "src/main/resources/images/" + type + "/" + fileName;
		Resource resource = new FileSystemResource(path);

		if (!resource.exists()) {
			throw new CustomException(ErrorCode.NOT_FOUND_IMAGE);
		}

		return resource;
	}

}

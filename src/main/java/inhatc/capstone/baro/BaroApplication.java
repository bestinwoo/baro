package inhatc.capstone.baro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BaroApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaroApplication.class, args);
	}

}

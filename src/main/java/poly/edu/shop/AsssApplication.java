package poly.edu.shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import poly.edu.shop.config.StorageProperties;
import poly.edu.shop.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class AsssApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsssApplication.class, args);
	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return(args->{
			storageService.init();
		});
	}
}

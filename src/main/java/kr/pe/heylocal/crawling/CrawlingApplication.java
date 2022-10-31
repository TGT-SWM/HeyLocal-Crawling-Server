package kr.pe.heylocal.crawling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CrawlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlingApplication.class, args);
	}

}

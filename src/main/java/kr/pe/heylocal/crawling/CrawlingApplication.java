package kr.pe.heylocal.crawling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"kr.pe.heylocal.crawling.repository"})
public class CrawlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlingApplication.class, args);
	}

}

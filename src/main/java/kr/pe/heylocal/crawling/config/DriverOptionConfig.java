/**
 * packageName    : kr.pe.heylocal.crawling.config
 * fileName       : DriverOptionConfig
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 웹드라이버 설정 Bean 객체를 등록하는 Configuration 클래스
 */

package kr.pe.heylocal.crawling.config;

import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverOptionConfig {
  @Value("${browser.driver.path}")
  public String webDriverId; //Properties 설정
  @Value("${browser.driver.id}")
  public String webDriverPath; //WebDriver 경로

  /**
   * 크롬 옵션 빈 객체 등록
   * @return 크롬 옵션 빈 객체
   */
  @Bean
  public ChromeOptions chromeOptions() {
    System.setProperty(webDriverId, webDriverPath);

    //WebDriver 옵션
    ChromeOptions options = new ChromeOptions();
    options.setHeadless(true);
    options.addArguments("--lang=ko");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--disable-gpu");
    options.addArguments("--ignore-certificate-errors");
    options.addArguments("--headless");
    options.addArguments("--incognito");
    options.addArguments("--verbose");

    return options;
  }
}

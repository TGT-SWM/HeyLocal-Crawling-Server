/**
 * packageName    : kr.pe.heylocal.crawling.crawler.driver
 * fileName       : CrawlingDriver
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 크롬 웹 드라이버를 드라이버 풀로 관리하기 위한 Wrapper 객체.
 *                  유휴 상태를 판단하기 위해, idleStatus 필드가 추가됨.
 *                  드라이버 풀에서 라이프사이클이 관리되므로, 프로토타입 빈으로 등록함.
 */

package kr.pe.heylocal.crawling.crawler.driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class CrawlingDriver extends ChromeDriver {
  private boolean idleStatus = true; //유휴 상태라면 true, 아니라면 false

  private CrawlingDriver(ChromeOptions chromeOptions) {
    super(chromeOptions);
  }

  public void setIdleStatus(boolean idleStatus) {
    this.idleStatus = idleStatus;
  }

  public boolean getIdleStatus() {
    return this.idleStatus;
  }
}

/**
 * packageName    : kr.pe.heylocal.crawling.crawler.task
 * fileName       : CrawlingTaskExecutor
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 크롤링 작업 실행 클래스
 */

package kr.pe.heylocal.crawling.crawler.task;

import kr.pe.heylocal.crawling.crawler.driver.CrawlingDriver;
import kr.pe.heylocal.crawling.crawler.driver.CrawlingDriverPool;
import kr.pe.heylocal.crawling.exception.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CrawlingTaskExecutor {
  private final CrawlingDriverPool crawlingDriverPool;
  private final CrawlingTask crawlingTask;

  /**
   * <pre>
   * 크롤링 작업을 수행하는 메서드.
   * 유휴 드라이버 객체를 가져와 크롤링을 수행함.
   * 드라이버 메모리 clearing 과 다시 유휴 상태로 변경하는 작업을 수행함.
   * </pre>
   * @param url 크롤링할 URL
   * @return 크롤링한 정보 결과
   */
  public Map<String, List<String>> execute(String url) throws ServiceUnavailableException {
    //유휴 Driver 가져오기
    CrawlingDriver driver = crawlingDriverPool.findIdleDriver();

    //크롤링 수행
    Map<String, List<String>> result = null;
    try {
      result = crawlingTask.doTask(driver, url);
    } catch (NoSuchElementException e) {
      throw new ServiceUnavailableException();
    } finally {
      //Driver Cache Clear
      driver.manage().deleteAllCookies();
      //Driver Status Change (유휴 상태로)
      driver.setIdleStatus(true);
    }

    return result;
  }
}

/**
 * packageName    : kr.pe.heylocal.crawling.crawler.driver
 * fileName       : CrawlingDriverPool
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 크롬 웹 드라이버를 관리하는 드라이버 풀.
 *                  총 MAX_POOL_SIZE 개의 드라이버를 생성·조회·종료 관리를 함.
 */

package kr.pe.heylocal.crawling.crawler.driver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlingDriverPool {
  public static final int MAX_POOL_SIZE = 5;
  private final ObjectFactory<CrawlingDriver> crawlingDriverObjectFactory;
  private List<CrawlingDriver> pool = new ArrayList<>();

  /**
   * <pre>
   * 유휴 드라이버를 조회하는 메서드
   * 여러 쓰레드가 한번에 조회시, 동일한 유휴 드라이버를 조회할 수 있으므로,
   * synchronized 처리함.
   * </pre>
   * @return 유휴 드라이버 객체
   */
  public synchronized CrawlingDriver findIdleDriver() {
    while (true) { //while 문 시작
      for (int i = 0; i < MAX_POOL_SIZE; i++) { //for 문 시작
        CrawlingDriver driver = pool.get(i);
        if (driver.getIdleStatus()) {
          driver.setIdleStatus(false); //유휴 상태에서 working 상태로 변경
          return driver;
        }
      } //for 문 종료
    } //while 문 종료
  }

  /**
   * <pre>
   * 드라이버 풀 초기화 메서드.
   * MAX_POOL_SIZE 만큼 드라이버를 생성하여 가지고 있음.
   * </pre>
   */
  @PostConstruct
  private void init() {
    for (int i = 0; i < MAX_POOL_SIZE; i++) {
      CrawlingDriver driver = crawlingDriverObjectFactory.getObject();
      pool.add(driver);
    }
  }

  /**
   * <pre>
   * 드라이버 풀 종료 메서드.
   * 가지고 있는 모든 드라이버를 종료 처리함.
   * </pre>
   */
  @PreDestroy
  private void closeAll() {
    for (int i = 0; i < MAX_POOL_SIZE; i++) {
      CrawlingDriver driver = pool.get(i);
      driver.quit();
    }
  }

}

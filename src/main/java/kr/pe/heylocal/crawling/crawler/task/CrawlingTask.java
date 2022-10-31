/**
 * packageName    : kr.pe.heylocal.crawling.crawler.task
 * fileName       : CrawlingTask
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 크롤링 작업 정의 인터페이스.
 */

package kr.pe.heylocal.crawling.crawler.task;

import kr.pe.heylocal.crawling.crawler.driver.CrawlingDriver;

import java.util.List;
import java.util.Map;

public interface CrawlingTask {

  /**
   * 수행할 작업 정의
   * @param crawlingDriver 사용할 드라이버 객체
   * @param url 크롤링할 URL
   * @return 크롤링한 정보
   */
  Map<String, List<String>> doTask(CrawlingDriver crawlingDriver, String url);
}

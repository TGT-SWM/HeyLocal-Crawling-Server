/**
 * packageName    : kr.pe.heylocal.crawling.crawler.util
 * fileName       : CrawlingProcessWatcher
 * author         : 우태균
 * date           : 2022/10/31
 * description    : 현재 어떤 크롤링 작업을 하는지 감시하기 위한 클래스.
 *                  -----------------[필요성]------------------
 *                  만약 '캐싱되지 않은 장소'에 대해서 여러 요청이 동시에 들어오면,
 *                  동일한 장소에 대해 크롤링을 여러번 해야 한다.
 *                  이를 방지하기 위해, 현재 어떤 장소에 대해 크롤링을 하고 있는지 확인하고,
 *                  이미 크롤링이 진행되고 있는 장소라면, 해당 요청을 처리하는 쓰레드를 wait 시킨 후,
 *                  크롤링이 완료되었을 때 notify 한다.
 *                  이를 통해, 동일한 장소에 대해 크롤링을 하지 않고,
 *                  (다른 요청으로부터 시작된) 크롤링이 끝나서 캐싱된 데이터를 가져올 수 있게 된다.
 */

package kr.pe.heylocal.crawling.crawler.util;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CrawlingProcessWatcher {
  //현재 크롤링 중인 장소 id 컬렉션
  private Set<String> nowProcessingIdSet = new HashSet<>();

  /**
   * <pre>
   * 크롤러 감시를 시작하는 메서드.
   * 크롤링을 시작할 때, 호출해줘야 한다.
   * 현재 어떤 장소에 대해 크롤링이 진행되는지 확인하고,
   * 동일한 장소에 대해 크롤링이 진행중이라면, 쓰레드를 wait시킨다.
   * </pre>
   * @param id 크롤링이 시작될 아이템 id
   */
  public synchronized void watch(String id) {
    if ( nowProcessingIdSet.contains(id) ) { //크롤링이 진행 중인 장소 id 라면
      try {
        wait(8000); //크롤링 작업이 끝날때까지 기다린다.
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      return;
    }
    nowProcessingIdSet.add(id);
  }

  /**
   * <pre>
   * 크롤러 감시를 종료하는 메서드.
   * 크롤링 작업이 끝났을 때, 호출해줘야 한다.
   * 현재 wait 된 쓰레드들을 모두 깨운다.
   * </pre>
   * @param id 크롤링이 종료될 아이템 id
   */
  public synchronized void done(String id) {
    nowProcessingIdSet.remove(id);
    notifyAll();
  }
}

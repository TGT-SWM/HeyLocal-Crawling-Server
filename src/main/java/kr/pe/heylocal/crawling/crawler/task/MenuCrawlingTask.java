/**
 * packageName    : kr.pe.heylocal.crawling.crawler.task
 * fileName       : MenuCrawlingTask
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 크롤링 작업 정의 클래스.
 *                  메뉴 정보(이름·가격)를 크롤링하는 클래스.
 */

package kr.pe.heylocal.crawling.crawler.task;

import kr.pe.heylocal.crawling.crawler.driver.CrawlingDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MenuCrawlingTask implements CrawlingTask {

  /**
   * 메뉴 정보를 얻는 크롤링 로직
   * @param crawlingDriver
   * @return
   */
  @Override
  public Map<String, String> doTask(CrawlingDriver crawlingDriver, String url) {
    Map<String, String> result = new HashMap<>();

    crawlingDriver.get(url); //페이지 로드
    crawlingDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    crawlingDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000)); //페이지 불러오는 여유시간

    List<WebElement> elementList = crawlingDriver.findElement(By.className("list_menu"))
        .findElements(By.className("nophoto_type"));

    //로드된 HTML에서 메뉴이름, 메뉴가격 가져오기
    elementList.stream().forEach(
        (element) -> {
          String menuName =
              element.findElement(By.className("info_menu")).findElement(By.className("loss_word")).getText();
          String menuPrice =
              element.findElement(By.className("info_menu")).findElement(By.className("price_menu")).getText();
          result.put(menuName, menuPrice);
        }
    );

    return result;
  }
}

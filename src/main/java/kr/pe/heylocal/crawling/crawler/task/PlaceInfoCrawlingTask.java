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
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class PlaceInfoCrawlingTask implements CrawlingTask {
  public static final String NO_PHOTO_CLASS = "nophoto_type";
  public static final String PHOTO_CLASS = "photo_type";

  /**
   * 메뉴 정보를 얻는 크롤링 로직
   * @param crawlingDriver
   * @return
   */
  @Override
  public Map<String, List<String>> doTask(CrawlingDriver crawlingDriver, String url) {
    Map<String, List<String>> result = new HashMap<>();

    crawlingDriver.get(url); //페이지 로드
    crawlingDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    crawlingDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000)); //페이지 불러오는 여유시간

    //메뉴정보 조회
    getMenuInfo(crawlingDriver, result);

    //영업시간 조회
    getBusinessTime(crawlingDriver, result);

    return result;
  }

  private void getMenuInfo(CrawlingDriver crawlingDriver, Map<String, List<String>> infoContainer) {
    //메뉴 더보기 클릭
    showMoreMenuItem(crawlingDriver);

    List<WebElement> subMenuElementList = crawlingDriver.findElement(By.className("list_menu"))
        .findElements(By.className(NO_PHOTO_CLASS));

    if (subMenuElementList.size() == 0) { //사진있는 메뉴인 경우
      subMenuElementList = crawlingDriver.findElement(By.className("list_menu"))
          .findElements(By.className(PHOTO_CLASS));

      //로드된 HTML에서 메뉴이름, 메뉴가격 가져오기
      getPhotoMenuInfo(subMenuElementList, infoContainer);

    } else { //사진없는 메뉴인 경우
      //로드된 HTML에서 메뉴이름, 메뉴가격 가져오기
      getNoPhotoMenuInfo(subMenuElementList, infoContainer);
    }

  }

  private void getBusinessTime(CrawlingDriver crawlingDriver, Map<String, List<String>> infoContainer) {
    boolean hasMoreBtn = true;
    List<String> businessTimeList = new ArrayList<>();

    //시간 더보기 버튼이 있는지 확인
    WebElement moreBtnElement = null;
    try {
      moreBtnElement = crawlingDriver.findElement(By.className("openhour_wrap"))
          .findElement(By.className("btn_more"));
    } catch (NoSuchElementException e) {
      hasMoreBtn = false;
    }

    List<WebElement> timeElements;
    if (hasMoreBtn) { //만약 시간 더보기 버튼이 있다면
      moreBtnElement.sendKeys("\uE007"); //클릭

      timeElements = crawlingDriver.findElement(By.className("inner_floor"))
          .findElement(By.className("list_operation"))
          .findElements(By.tagName("li"));

    } else { //만약 시간 더보기 버튼이 없다면
      timeElements = crawlingDriver.findElement(By.className("openhour_wrap"))
          .findElement(By.className("location_present"))
          .findElement(By.className("list_operation"))
          .findElements(By.tagName("li"));
    }

    //String Formatting
    timeElements.stream().forEach(element -> businessTimeList.add(element.getText()));

    //결과 Containing
    infoContainer.put("__bt__", businessTimeList);
  }

  private void getNoPhotoMenuInfo(List<WebElement> subMenuElementList, Map<String, List<String>> infoContainer) {
    subMenuElementList.stream().forEach(
        (element) -> {
          String menuName =
              element.findElement(By.className("info_menu")).findElement(By.className("loss_word")).getText();
          String menuPrice =
              element.findElement(By.className("info_menu")).findElement(By.className("price_menu")).getText();
          ArrayList<String> info = new ArrayList<>();
          info.add(menuPrice);
          infoContainer.put(menuName, info);
        }
    );
  }

  private void getPhotoMenuInfo(List<WebElement> subMenuElementList, Map<String, List<String>> infoContainer) {
    subMenuElementList.stream().forEach(
        (element) -> {
          String menuName =
              element.findElement(By.className("info_menu")).findElement(By.className("loss_word")).getText();
          String menuPrice =
              element.findElement(By.className("info_menu")).findElement(By.className("price_menu")).getText();
          String menuPhoto =
              element.findElement(By.className("img_thumb")).getAttribute("src");
          ArrayList<String> info = new ArrayList<>();
          info.add(menuPrice);
          info.add(menuPhoto);
          infoContainer.put(menuName, info);
        }
    );

  }

  /**
   * 메뉴 더보기 클릭
   * @param crawlingDriver
   */
  private void showMoreMenuItem(CrawlingDriver crawlingDriver) {
    WebElement element = crawlingDriver.findElement(By.className("cont_menu"));

    try {
      element = element.findElement(By.className("link_more"));
    } catch (NoSuchElementException e) {
      return;
    }
    element.click();
  }
}

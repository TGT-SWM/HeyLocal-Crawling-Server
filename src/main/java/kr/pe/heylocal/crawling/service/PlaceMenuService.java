/**
 * packageName    : kr.pe.heylocal.crawling.service
 * fileName       : CrawlingService
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 크롤링 서비스
 */

package kr.pe.heylocal.crawling.service;

import kr.pe.heylocal.crawling.crawler.task.CrawlingTaskExecutor;
import kr.pe.heylocal.crawling.domain.Menu;
import kr.pe.heylocal.crawling.dto.MenuDto;
import kr.pe.heylocal.crawling.exception.ServiceUnavailableException;
import kr.pe.heylocal.crawling.mapper.MenuMapper;
import kr.pe.heylocal.crawling.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceMenuService {
  @Value("${url.kakao.place}")
  public String kakaoPlaceDomain; //카카오 장소 도메인 주소
  private final CrawlingTaskExecutor crawlingTaskExecutor; //크롤링 작업 수행 객체
  private final MenuRepository menuRepository;

  /**
   * 메뉴 정보 조회 메서드
   * @param placeId
   * @return
   */
  @Transactional
  public List<MenuDto> inquiryMenuInfoOfPlace(String placeId) throws ServiceUnavailableException {
    List<MenuDto> result;
    List<Menu> cachedResult = menuRepository.findByPlaceId(placeId);

    if (cachedResult.size() != 0) { //만약 캐싱되어 있다면
      log.info("캐싱 출력");
      result = MenuMapper.INSTANCE.toMenuDtoList(cachedResult);

    } else { //만약 캐싱되어 있지 않다면
      log.info("크롤링 출력");
      Map<String, String> crawlingResult = getMenuInfoFromCrawler(placeId);
      result = MenuMapper.INSTANCE.toMenuDtoList(crawlingResult);
      saveMenu(placeId, result);
    }

    //Map<String,String> -> List<MenuDto>
    return result;
  }

  private void saveMenu(String placeId, List<MenuDto> result) {
    result.stream().forEach((item) -> {
      Menu menuEntity = MenuMapper.INSTANCE.toMenuEntity(item, placeId);
      menuRepository.save(menuEntity);
    });
  }

  private Map<String, String> getMenuInfoFromCrawler(String placeId) throws ServiceUnavailableException {
    String targetUrl = kakaoPlaceDomain + "/" + placeId; //크롤링할 URL
    return crawlingTaskExecutor.execute(targetUrl); //크롤링
  }
}

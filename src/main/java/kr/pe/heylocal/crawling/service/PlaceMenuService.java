/**
 * packageName    : kr.pe.heylocal.crawling.service
 * fileName       : PlaceMenuService
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 장소 메뉴 서비스
 */

package kr.pe.heylocal.crawling.service;

import kr.pe.heylocal.crawling.crawler.util.CrawlingProcessWatcher;
import kr.pe.heylocal.crawling.dto.MenuDto;
import kr.pe.heylocal.crawling.exception.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceMenuService {
  @Value("${url.kakao.place}")
  public String kakaoPlaceDomain; //카카오 장소 도메인 주소
  private final CrawlingService crawlingService;
  private final CacheService cacheService;
  private final CrawlingProcessWatcher crawlingProcessWatcher;

  /**
   * 메뉴 정보 조회 메서드
   * @param placeId
   * @return
   */
  public List<MenuDto> inquiryMenuInfoOfPlace(String placeId) throws ServiceUnavailableException {
    List<MenuDto> result;

    crawlingProcessWatcher.watch(placeId); //크롤링 감시 시작

    result = cacheService.inquiryMenu(placeId);
    if (result.size() != 0) { //만약 캐싱되어 있다면
      return result;

    } else { //만약 캐싱되어 있지 않다면
      result = crawlingService.crawlingMenu(placeId);
    }

    crawlingProcessWatcher.done(placeId); //크롤링 감시 종료

    return result;
  }

}

/**
 * packageName    : kr.pe.heylocal.crawling.service
 * fileName       : PlaceMenuService
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 장소 메뉴 서비스
 */

package kr.pe.heylocal.crawling.service;

import kr.pe.heylocal.crawling.crawler.util.CrawlingProcessWatcher;
import kr.pe.heylocal.crawling.dto.PlaceInfoDto;
import kr.pe.heylocal.crawling.exception.NotFoundException;
import kr.pe.heylocal.crawling.exception.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceMenuService {
  public static final int CACHE_UPDATE_DAYS = 14; //캐시 데이터 업데이트 기준일
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
  public PlaceInfoDto inquiryMenuInfoOfPlace(long placeId) throws ServiceUnavailableException, NotFoundException {
    PlaceInfoDto result;
    crawlingProcessWatcher.watch(placeId); //크롤링 감시 시작

    LocalDateTime savedDateTime = cacheService.getSavedDateTime(placeId);

    if (savedDateTime != null) { //만약 캐싱되어 있고,
      boolean isOldCacheData = isOldCacheData(savedDateTime);

      if (!isOldCacheData) { //오래된 데이터가 아니라면
        result = cacheService.inquiryPlaceInfo(placeId); //DB에서 장소 정보 조회
        crawlingProcessWatcher.done(placeId); //크롤링 감시 종료
        return result;

      } else { //오래된 데이터라면
        cacheService.removePlaceInfo(placeId); //DB에 저장된 메뉴 정보 삭제
      }
    }

    //만약 캐싱되어 있지 않다면
    result = crawlingService.crawlingPlaceInfoAndSave(placeId); //크롤러로 장소 정보 조회

    crawlingProcessWatcher.done(placeId); //크롤링 감시 종료

    return result;
  }

  private boolean isOldCacheData(LocalDateTime lastSavedDateTime) {
    LocalDateTime liveDateTime = LocalDateTime.now().minusDays(CACHE_UPDATE_DAYS);
    if (lastSavedDateTime.isBefore(liveDateTime)) return true;
    return false;
  }

}

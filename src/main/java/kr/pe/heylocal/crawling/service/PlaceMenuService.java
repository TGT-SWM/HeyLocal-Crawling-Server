/**
 * packageName    : kr.pe.heylocal.crawling.service
 * fileName       : CrawlingService
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 크롤링 서비스
 */

package kr.pe.heylocal.crawling.service;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceMenuService {
  @Value("${url.kakao.place}")
  public String kakaoPlaceDomain; //카카오 장소 도메인 주소
  private final MenuRepository menuRepository;
  private final CrawlingService crawlingService;

  /**
   * 메뉴 정보 조회 메서드
   * @param placeId
   * @return
   */
  @Transactional
  public List<MenuDto> inquiryMenuInfoOfPlace(String placeId) throws ServiceUnavailableException {
    List<MenuDto> result;
    List<Menu> cachedResult;

    cachedResult = menuRepository.findByPlaceId(placeId);
    if (cachedResult.size() != 0) { //만약 캐싱되어 있다면
      log.info("캐싱 출력");
      result = MenuMapper.INSTANCE.toMenuDtoList(cachedResult);

    } else { //만약 캐싱되어 있지 않다면
      log.info("크롤링 출력");
      result = crawlingService.crawlingMenu(placeId);
    }

    return result;
  }

}

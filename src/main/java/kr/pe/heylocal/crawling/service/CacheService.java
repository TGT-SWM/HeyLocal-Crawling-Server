/**
 * packageName    : kr.pe.heylocal.crawling.service
 * fileName       : CacheService
 * author         : 우태균
 * date           : 2022/10/31
 * description    : 캐싱 서비스
 */

package kr.pe.heylocal.crawling.service;

import kr.pe.heylocal.crawling.domain.Menu;
import kr.pe.heylocal.crawling.dto.MenuDto;
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
public class CacheService {
  @Value("${url.kakao.place}")
  public String kakaoPlaceDomain; //카카오 장소 도메인 주소
  private final MenuRepository menuRepository;

  /**
   * DB에서 메뉴정보 조회
   * @param placeId 조회할 장소 Id
   * @return
   */
  @Transactional
  public List<MenuDto> inquiryMenu(String placeId) {
    List<Menu> cachedResult;

    cachedResult = menuRepository.findByPlaceId(placeId);

    return MenuMapper.INSTANCE.toMenuDtoList(cachedResult);
  }
}

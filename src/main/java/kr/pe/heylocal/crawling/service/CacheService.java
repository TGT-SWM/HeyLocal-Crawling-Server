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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
  @Transactional(readOnly = true)
  public List<MenuDto> inquiryMenu(String placeId) {
    List<Menu> cachedResult;

    cachedResult = menuRepository.findByPlaceId(placeId);

    return MenuMapper.INSTANCE.toMenuDtoList(cachedResult);
  }

  /**
   * 해당 장소 ID의 메뉴 정보가 마지막으로 저장된 일자 조회
   * @param placeId 장소 ID
   * @return 마지막 수정일시, 만약 장소 id에 해당하는 메뉴 정보가 존재하지 않는다면 null
   */
  @Transactional(readOnly = true)
  public LocalDateTime getSavedDateTime(String placeId) {
    Optional<Menu> menuOptional = menuRepository.findFirstByPlaceId(placeId);
    if (menuOptional.isEmpty()) return null;
    return menuOptional.get().getModifiedDate();
  }

  @Transactional
  public void removeAllMenuByPlaceId(String placeId) {
    menuRepository.removeAllByPlaceId(placeId);
  }
}

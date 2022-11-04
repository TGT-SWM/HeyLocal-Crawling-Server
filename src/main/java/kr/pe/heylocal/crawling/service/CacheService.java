/**
 * packageName    : kr.pe.heylocal.crawling.service
 * fileName       : CacheService
 * author         : 우태균
 * date           : 2022/10/31
 * description    : 캐싱 서비스
 */

package kr.pe.heylocal.crawling.service;

import kr.pe.heylocal.crawling.domain.PlaceInfo;
import kr.pe.heylocal.crawling.dto.PlaceInfoDto;
import kr.pe.heylocal.crawling.exception.NotFoundException;
import kr.pe.heylocal.crawling.mapper.PlaceInfoMapper;
import kr.pe.heylocal.crawling.repository.MenuRepository;
import kr.pe.heylocal.crawling.repository.PlaceInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {
  @Value("${url.kakao.place}")
  public String kakaoPlaceDomain; //카카오 장소 도메인 주소
  private final MenuRepository menuRepository;
  private final PlaceInfoRepository placeInfoRepository;

  /**
   * DB에서 장소정보 조회
   * @param placeId 조회할 장소 Id
   * @return
   */
  @Transactional(readOnly = true)
  public PlaceInfoDto inquiryPlaceInfo(long placeId) throws NotFoundException {
    PlaceInfo placeInfo = placeInfoRepository.findById(placeId).orElseThrow(
        () -> new NotFoundException()
    );

    return PlaceInfoMapper.INSTANCE.toDto(placeInfo);
  }

  /**
   * 해당 ID의 PlaceInfo가 마지막으로 저장된 일자 조회
   * @param placeId 장소 ID
   * @return 마지막 수정일시, 만약 해당 id의 장소정보가 존재하지 않는다면 null
   */
  @Transactional(readOnly = true)
  public LocalDateTime getSavedDateTime(long placeId) {
    Optional<PlaceInfo> placeInfoOptional = placeInfoRepository.findById(placeId);
    if (placeInfoOptional.isEmpty()) return null;
    return placeInfoOptional.get().getModifiedDate();
  }

  @Transactional
  public void removePlaceInfo(long placeId) {
    placeInfoRepository.deleteById(placeId);
  }
}

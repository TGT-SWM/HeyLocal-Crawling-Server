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
import kr.pe.heylocal.crawling.domain.PlaceInfo;
import kr.pe.heylocal.crawling.dto.BusinessTimeDto;
import kr.pe.heylocal.crawling.dto.MenuDto;
import kr.pe.heylocal.crawling.dto.PlaceInfoDto;
import kr.pe.heylocal.crawling.exception.ServiceUnavailableException;
import kr.pe.heylocal.crawling.mapper.BusinessTimeMapper;
import kr.pe.heylocal.crawling.mapper.MenuMapper;
import kr.pe.heylocal.crawling.mapper.PlaceInfoMapper;
import kr.pe.heylocal.crawling.repository.MenuRepository;
import kr.pe.heylocal.crawling.repository.PlaceInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CrawlingService {
  @Value("${url.kakao.place}")
  public String kakaoPlaceDomain; //카카오 장소 도메인 주소
  private final CrawlingTaskExecutor crawlingTaskExecutor; //크롤링 작업 수행 객체
  private final PlaceInfoRepository placeInfoRepository;

  /**
   * 크롤링으로 메뉴 정보를 조회하고 저장하는 메서드
   * @param placeId
   * @return
   */
  @Transactional
  public PlaceInfoDto crawlingPlaceInfoAndSave(long placeId) throws ServiceUnavailableException {
    PlaceInfoDto result;
    Map<String, List<String>> crawlingResult = getMenuInfoFromCrawler(placeId);

    //crawlingResult 에서 영업시간만 분리
    List<String> businessTimeList = crawlingResult.get("__bt__");
    crawlingResult.remove("__bt__");

    //영업 시간 정보 처리
    List<BusinessTimeDto> businessTimeDtoList = BusinessTimeMapper.INSTANCE.toDtoList(businessTimeList);

    //메뉴 정보 처리
    List<MenuDto> menuDtoList = MenuMapper.INSTANCE.toDtoList(crawlingResult);

    //result 바인딩
    result = PlaceInfoMapper.INSTANCE.toDto(menuDtoList, businessTimeDtoList);

    //새 PlaceInfo 엔티티 저장
    PlaceInfo placeInfoEntity = PlaceInfoMapper.INSTANCE.toEntity(result, placeId);
    placeInfoRepository.save(placeInfoEntity);

    return result;
  }

  private Map<String, List<String>> getMenuInfoFromCrawler(long placeId) throws ServiceUnavailableException {
    String targetUrl = kakaoPlaceDomain + "/" + placeId; //크롤링할 URL
    return crawlingTaskExecutor.execute(targetUrl); //크롤링
  }
}

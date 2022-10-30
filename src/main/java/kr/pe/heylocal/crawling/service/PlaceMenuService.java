/**
 * packageName    : kr.pe.heylocal.crawling.service
 * fileName       : CrawlingService
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 크롤링 서비스
 */

package kr.pe.heylocal.crawling.service;

import kr.pe.heylocal.crawling.crawler.task.CrawlingTaskExecutor;
import kr.pe.heylocal.crawling.dto.MenuDto;
import kr.pe.heylocal.crawling.exception.ServiceUnavailableException;
import kr.pe.heylocal.crawling.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PlaceMenuService {
  @Value("${url.kakao.place}")
  public String kakaoPlaceDomain; //카카오 장소 도메인 주소
  private final CrawlingTaskExecutor crawlingTaskExecutor; //크롤링 작업 수행 객체

  /**
   * 메뉴 정보 조회 메서드
   * @param placeId
   * @return
   */
  public List<MenuDto> inquiryMenuInfoOfPlace(String placeId) throws ServiceUnavailableException {
    String targetUrl = kakaoPlaceDomain + "/" + placeId; //크롤링할 URL
    Map<String, String> result = crawlingTaskExecutor.execute(targetUrl); //크롤링

    //Map<String,String> -> List<MenuDto>
    return MenuMapper.INSTANCE.toMenuDtoList(result);
  }
}

/**
 * packageName    : kr.pe.heylocal.crawling.controller
 * fileName       : PlaceController
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 장소 컨트롤러
 */

package kr.pe.heylocal.crawling.controller;

import kr.pe.heylocal.crawling.dto.MenuDto;
import kr.pe.heylocal.crawling.exception.ServiceUnavailableException;
import kr.pe.heylocal.crawling.service.PlaceMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {
  private final PlaceMenuService placeMenuService;

  @GetMapping("/menu")
  public List<MenuDto> test(@RequestParam String placeId) throws ServiceUnavailableException {
    return placeMenuService.inquiryMenuInfoOfPlace(placeId);
  }
}

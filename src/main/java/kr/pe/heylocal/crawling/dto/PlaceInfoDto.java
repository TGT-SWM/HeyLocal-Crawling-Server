/**
 * packageName    : kr.pe.heylocal.crawling.dto
 * fileName       : PlaceDto
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 장소 정보 DTO 클래스
 */

package kr.pe.heylocal.crawling.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceInfoDto {
  private List<BusinessTimeDto> businessTimes;
  private List<MenuDto> menus;
}

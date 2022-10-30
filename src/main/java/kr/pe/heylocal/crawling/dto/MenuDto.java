/**
 * packageName    : kr.pe.heylocal.crawling.dto
 * fileName       : MenuDto
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 메뉴 정보 DTO 클래스
 */

package kr.pe.heylocal.crawling.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDto {
  private String name;
  private String price;
}

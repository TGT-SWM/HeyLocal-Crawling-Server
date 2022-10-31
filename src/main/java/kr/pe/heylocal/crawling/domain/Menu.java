/**
 * packageName    : kr.pe.heylocal.crawling.domain
 * fileName       : Menu
 * author         : 우태균
 * date           : 2022/10/31
 * description    : 메뉴 엔티티
 */

package kr.pe.heylocal.crawling.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "MENU")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {
  @Id
  @GeneratedValue
  private Long id;
  private String placeId; //카카오 장소 API 에서 제공하는 장소 ID
  private String name;
  private String price;
}

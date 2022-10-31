/**
 * packageName    : kr.pe.heylocal.crawling.domain
 * fileName       : Menu
 * author         : 우태균
 * date           : 2022/10/31
 * description    : 메뉴 엔티티
 */

package kr.pe.heylocal.crawling.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Entity
@Table(name = "MENU",
    uniqueConstraints={
        @UniqueConstraint(
            name="complex_unique",
            columnNames={"PLACE_ID", "NAME"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Menu extends BaseTimeEntity {
  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "PLACE_ID")
  private String placeId; //카카오 장소 API 에서 제공하는 장소 ID
  @Column(name = "NAME")
  private String name;
  private String price;
  private String photo;
}

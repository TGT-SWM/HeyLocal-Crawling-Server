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
            columnNames={"PLACE_INFO_ID", "NAME"}
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
  @Column(name = "NAME")
  private String name;
  private String price;
  private String photo;
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "PLACE_INFO_ID")
  private PlaceInfo placeInfo;

  public void setPlaceInfo(PlaceInfo placeInfo) {
    if (this.placeInfo != null && this.placeInfo != placeInfo) {
      this.placeInfo.removeMenu(this);
    }
    placeInfo.addMenu(this);
    this.placeInfo = placeInfo;
  }
}

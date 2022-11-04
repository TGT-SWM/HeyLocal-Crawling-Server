package kr.pe.heylocal.crawling.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PLACE_INFO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PlaceInfo extends BaseTimeEntity {
  @Id
  private Long id; //카카오 장소 API 에서 제공하는 장소 ID

  //양방향 관계
  @Builder.Default
  @OneToMany(mappedBy = "placeInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Menu> menuList = new ArrayList<>();
  @Builder.Default
  @OneToMany(mappedBy = "placeInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<BusinessTime> businessTimeList = new ArrayList<>();

  public void addMenu(Menu menu) {
    if (menuList.contains(menu)) return;
    menuList.add(menu);
  }

  public void removeMenu(Menu menu) {
    menuList.remove(menu);
  }

  public void addBusinessTime(BusinessTime businessTime) {
    if (businessTimeList.contains(businessTime)) return;
    businessTimeList.add(businessTime);
  }

  public void removeBusinessTime(BusinessTime businessTime) {
    businessTimeList.remove(businessTime);
  }
}

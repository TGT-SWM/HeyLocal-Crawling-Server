package kr.pe.heylocal.crawling.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "BUSINESS_TIME")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BusinessTime extends BaseTimeEntity {
  @Id @GeneratedValue
  private Long id;
  private String openTime;
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private PlaceInfo placeInfo;

  public void setPlaceInfo(PlaceInfo placeInfo) {
    if (this.placeInfo != null && this.placeInfo != placeInfo) {
      this.placeInfo.removeBusinessTime(this);
    }
    placeInfo.addBusinessTime(this);
    this.placeInfo = placeInfo;
  }
}

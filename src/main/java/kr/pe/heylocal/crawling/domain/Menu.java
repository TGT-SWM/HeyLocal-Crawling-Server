package kr.pe.heylocal.crawling.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {
  @Id
  private String id;
  private String placeId; //카카오 장소 API 에서 제공하는 장소 ID
  private String name;
  private String price;
}

/**
 * packageName    : kr.pe.heylocal.crawling.dto
 * fileName       : ErrorMessageDto
 * author         : 우태균
 * date           : 2022/10/31
 * description    : 에러 응답 DTO
 */

package kr.pe.heylocal.crawling.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessageDto {
  private String message;
}

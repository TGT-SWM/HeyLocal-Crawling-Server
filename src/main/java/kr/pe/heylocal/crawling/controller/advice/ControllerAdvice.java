/**
 * packageName    : kr.pe.heylocal.crawling.controller.advice
 * fileName       : ControllerAdvice
 * author         : 우태균
 * date           : 2022/10/31
 * description    : 에러 응답 처리 어드바이스
 */

package kr.pe.heylocal.crawling.controller.advice;

import kr.pe.heylocal.crawling.dto.ErrorMessageDto;
import kr.pe.heylocal.crawling.exception.ServiceUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
  @ExceptionHandler(ServiceUnavailableException.class)
  @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
  public ErrorMessageDto serviceUnavailableException(ServiceUnavailableException e) {
    return ErrorMessageDto.builder().message(e.getMessage()).build();
  }
}

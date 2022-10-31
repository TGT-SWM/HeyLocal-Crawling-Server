/**
 * packageName    : kr.pe.heylocal.crawling.exception
 * fileName       : ServiceUnavailableException
 * author         : 우태균
 * date           : 2022/10/31
 * description    : HTTP 503 에러 예외
 */

package kr.pe.heylocal.crawling.exception;

public class ServiceUnavailableException extends Exception {
  public static final int ERROR_CODE = 503;
  public static final String ERROR_MESSAGE = "요청을 처리하는데, 오래 걸립니다. 다시 시도해주세요.";

  public ServiceUnavailableException() {
    super(ERROR_MESSAGE);
  }
}

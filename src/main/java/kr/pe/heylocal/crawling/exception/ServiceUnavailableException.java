package kr.pe.heylocal.crawling.exception;

public class ServiceUnavailableException extends Exception {
  public static final String ERROR_MESSAGE = "요청을 처리하는데, 오래 걸립니다. 다시 시도해주세요.";

  public ServiceUnavailableException() {
    super(ERROR_MESSAGE);
  }
}

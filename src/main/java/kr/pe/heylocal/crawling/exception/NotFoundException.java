package kr.pe.heylocal.crawling.exception;

public class NotFoundException extends Exception {
  public static final int ERROR_CODE = 404;
  public static final String ERROR_MESSAGE = "찾을 수 없는 정보입니다.";

  public NotFoundException() {
    super(ERROR_MESSAGE);
  }
}

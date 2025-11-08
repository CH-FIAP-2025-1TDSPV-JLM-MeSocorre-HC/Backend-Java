package br.com.hc.exception;

public class HcException extends RuntimeException {
    public HcException(String message) {
        super(message);
    }

  public HcException(String message, Throwable cause) {
    super(message, cause);
  }
}

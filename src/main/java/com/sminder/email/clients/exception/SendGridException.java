package com.sminder.email.clients.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class SendGridException extends RuntimeException {

  private String errorMessage;
  private HttpStatus httpStatus;

  public SendGridException(String errorMessage, HttpStatus httpStatus) {
    super(errorMessage);
    this.errorMessage = errorMessage;
    this.httpStatus = httpStatus;
  }

}

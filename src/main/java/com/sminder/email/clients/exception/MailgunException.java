package com.sminder.email.clients.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class MailgunException extends RuntimeException {

  private String message;
  private HttpStatus httpStatus;

  public MailgunException(String message, HttpStatus status) {
    super(message);
    this.message = message;
    this.httpStatus = status;
  }


}

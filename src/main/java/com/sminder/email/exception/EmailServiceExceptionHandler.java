package com.sminder.email.exception;

import com.sminder.email.clients.exception.MailgunException;
import com.sminder.email.clients.exception.SendGridException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "com.sminder.email")
public class EmailServiceExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = MailgunException.class)
  public ResponseEntity<EmailServiceException> handle(MailgunException e) {
    log.error("MailgunException controlleradvice: ", e);
    List<String> errors = new ArrayList<>();
    errors.add(e.getMessage());
    HttpStatus httpStatus = e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
    EmailServiceException emailServiceException = EmailServiceException.builder().status(httpStatus.value()).message(httpStatus).details(errors)
        .build();

    return ResponseEntity.status(httpStatus).body(emailServiceException);

  }

  @ExceptionHandler(value = SendGridException.class)
  public ResponseEntity<EmailServiceException> handle(SendGridException e) {
    log.error("SendGridException controlleradvice: ", e);
    List<String> errors = new ArrayList<>();
    errors.add(e.getErrorMessage());
    EmailServiceException emailServiceException = EmailServiceException.builder().status(e.getHttpStatus().value()).message(e.getHttpStatus())
        .details(errors).build();

    if (e.getHttpStatus() != null) {
      return ResponseEntity.status(e.getHttpStatus()).body(emailServiceException);
    }

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailServiceException);

  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<EmailServiceException> handle(Exception e) {
    logger.error("Exception method invoked {} ", e);
    List<String> errors = new ArrayList<>();
    errors.add(e.getMessage());
    log.error("Exception block {}", e.getMessage());
    EmailServiceException emailServiceException = EmailServiceException.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message(HttpStatus.INTERNAL_SERVER_ERROR).details(errors).build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailServiceException);

  }


  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {

    List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getField() + ": " + x.getDefaultMessage())
        .collect(Collectors.toList());

    return new ResponseEntity<>(EmailServiceException.builder().details(errors).status(status.value()).message(status).build(), headers, status);
  }
}

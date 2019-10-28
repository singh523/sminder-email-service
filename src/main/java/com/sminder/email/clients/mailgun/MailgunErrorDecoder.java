package com.sminder.email.clients.mailgun;

import com.sminder.email.clients.exception.MailgunException;
import com.sminder.email.clients.mailgun.model.MailgunResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Slf4j
public class MailgunErrorDecoder implements ErrorDecoder {

  private ObjectMapper objectMapper;

  @Autowired
  public MailgunErrorDecoder(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Exception decode(String s, Response response) {

    log.error("Exception message {}", response);

    String body;
    MailgunResponse mailgunResponse;
    try {
      body = Streams.asString(response.body().asInputStream());
      log.info("MailgunErrorDecoder Body {}", body);
      mailgunResponse = objectMapper.readValue(body, MailgunResponse.class);
      log.error("mailgunResponse message: {} {}", mailgunResponse.getMessage(), response.status());
    } catch (Exception e) {
      log.error("Generic error, failed to read body: {}", response);
      throw new MailgunException("Unknown generic error", HttpStatus.valueOf(response.status()));
    }
    throw new MailgunException(mailgunResponse.getMessage(), HttpStatus.valueOf(response.status()));
  }

}

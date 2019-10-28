package com.sminder.email.clients.sendgrid;

import com.sminder.email.clients.exception.SendGridException;
import com.sminder.email.clients.sendgrid.model.ErrorDetails;
import com.sminder.email.clients.sendgrid.model.SendGridResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Slf4j
public class SendGridErrorDecoder implements ErrorDecoder {

  private ObjectMapper objectMapper;

  @Autowired
  public SendGridErrorDecoder(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Exception decode(String s, Response response) {

    log.error("Exception message {}", response);

    String body;
    SendGridResponse sendGridResponse;

    if (response.status() == 401) {
      throw new SendGridException("Please use the correct sendgrid api credentials", HttpStatus.valueOf(response.status()));
    }
    try {
      body = Streams.asString(response.body().asInputStream());
      log.debug("SendGridErrorDecoder Body {}", body);
      sendGridResponse = objectMapper.readValue(body, SendGridResponse.class);
      log.error("sendgridresponse message: {}" + sendGridResponse.getMessage());
    } catch (Exception e) {
      log.error("Generic error, failed to read body: {}", response);
      return new SendGridException("Unknown generic error", HttpStatus.valueOf(response.status()));
    }
    log.error("Error message from sendgriderrordecoder {}", sendGridResponse.getMessage());
    throw new SendGridException(buildErrorMessages(sendGridResponse.getErrors()), sendGridResponse.getStatus());
  }


  private String buildErrorMessages(List<ErrorDetails> list) {

    StringBuilder sb = new StringBuilder();
    list.stream().forEach(x -> sb.append(x.getField()).append("-").append(x.getMessage()));
    return sb.toString();
  }
}

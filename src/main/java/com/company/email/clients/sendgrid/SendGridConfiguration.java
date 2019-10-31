package com.company.email.clients.sendgrid;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Slf4j
public class SendGridConfiguration {

  @Value("${email.sendgrid-service.token}")
  private String sendGridServiceToken;

  @Value("${email.sendgrid-service.connectTimeOutMillis:12000}")
  private int connectTimeOutMillis;

  @Value("${email.sendgrid-service.readTimeOutMillis:12000}")
  private int readTimeOutMillis;

  @Autowired
  private ObjectMapper objectMapper;

  @Bean
  public ErrorDecoder errorDecoder() {
    return new SendGridErrorDecoder(objectMapper);
  }

  @Bean
  public Request.Options options() {
    return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
  }

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> requestTemplate.header("Authorization", this.sendGridServiceToken);
  }
}

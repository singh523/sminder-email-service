package com.sminder.email.clients.mailgun;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class MailgunConfiguration {

  @Value("${email.mailgun-service.username}")
  private String username;

  @Value("${email.mailgun-service.password}")
  private String password;

  @Autowired
  private ObjectMapper objectMapper;

  @Bean
  public ErrorDecoder errorDecoder() {
    return new MailgunErrorDecoder(objectMapper);
  }

  @Bean
  public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
    return new BasicAuthRequestInterceptor(username, password);
  }
}

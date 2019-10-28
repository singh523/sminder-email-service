package com.sminder.email.service;

import com.sminder.email.clients.sendgrid.SendGridServiceClient;
import com.sminder.email.clients.sendgrid.model.Content;
import com.sminder.email.clients.sendgrid.model.Email;
import com.sminder.email.clients.sendgrid.model.Personalization;
import com.sminder.email.clients.sendgrid.model.SendGridRequest;
import com.sminder.email.model.EmailRequest;
import com.sminder.email.model.EmailResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
public class EmailService {

  private SendGridServiceClient sendGridServiceClient;

  @Autowired
  EmailService(final @NotNull @Qualifier("sendgrid-service") SendGridServiceClient sendGridServiceClient) {
    this.sendGridServiceClient = sendGridServiceClient;
  }

  public EmailResponse sendEmail(final EmailRequest emailRequest) {
    ResponseEntity responseEntity = sendGridServiceClient.sendEmail(buildSendGridRequest(emailRequest));
    log.debug("Response received from mail service providers {}", responseEntity.getStatusCode());
    return EmailResponse.builder().status(responseEntity.getStatusCode()).message("Message accepted successfully").build();
  }

  private SendGridRequest buildSendGridRequest(final EmailRequest emailRequest) {
    Personalization personalization = Personalization.builder().tos(buildEmailIds(emailRequest.getToRecipients()))
        .bccs(buildEmailIds(emailRequest.getBccRecipients())).ccs(buildEmailIds(emailRequest.getCcRecipients())).subject(emailRequest.getSubject())
        .build();

    return SendGridRequest.builder().personalizations(Collections.singletonList(personalization))
        .from(Email.builder().email(emailRequest.getFrom()).build()).
            content(buildContent(emailRequest.getBody(), "text/plain")).build();
  }


  private List<Content> buildContent(String body, String type) {
    return Collections.singletonList(Content.builder().type(type).value(body).build());
  }

  private List<Email> buildEmailIds(List<String> emailIds) {
    return emailIds != null ? emailIds.stream().map(x -> Email.builder().email(x).build()).collect(Collectors.toList()) : null;
  }


}

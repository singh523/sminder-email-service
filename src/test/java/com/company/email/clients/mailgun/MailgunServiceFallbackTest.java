package com.company.email.clients.mailgun;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.company.email.clients.exception.MailgunException;
import com.company.email.clients.mailgun.model.MailgunResponse;
import com.company.email.clients.sendgrid.model.Content;
import com.company.email.clients.sendgrid.model.Email;
import com.company.email.clients.sendgrid.model.Personalization;
import com.company.email.clients.sendgrid.model.SendGridRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class MailgunServiceFallbackTest {

  @Mock
  private MailgunServiceClient mailgunServiceClient;

  private MailgunServiceFallback mailgunServiceFallback;

  @Before
  public void setUp() {
    mailgunServiceFallback = new MailgunServiceFallback(mailgunServiceClient);
  }

  @Test
  public void sendmail_should_success_for_valid_input() {

    MailgunResponse mailgunResponse = MailgunResponse.builder().id("someid").message("Queued. Thank you.").build();

    List<Personalization> personalizations = new ArrayList<>();

    Personalization personalization = Personalization.builder().subject("subject").ccs(buildEMailList("cc@test.com")).
        tos(buildEMailList("xyz@test.com")).build();

    personalizations.add(personalization);
    SendGridRequest sendGridRequest = SendGridRequest.builder().from(Email.builder().email("from@test.com").build())
        .content(buildContent("email body")).personalizations(personalizations).build();

    when(mailgunServiceClient.sendEmail(anyMap())).thenReturn(mailgunResponse);

    ResponseEntity result = mailgunServiceFallback.sendEmail(sendGridRequest);

    assertNotNull(result);
    assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    verify(mailgunServiceClient).sendEmail(anyMap());

  }

  @Test(expected = MailgunException.class)
  public void sendmail_should_throw_mailgunexception_for_client_exception() {

    List<Personalization> personalizations = new ArrayList<>();

    Personalization personalization = Personalization.builder().subject("subject of email ").ccs(buildEMailList("cc@test.com")).
        tos(buildEMailList("xssyz@test.com")).build();

    personalizations.add(personalization);
    SendGridRequest sendGridRequest = SendGridRequest.builder().from(Email.builder().email("from@test.com").build())
        .content(buildContent("email body")).personalizations(personalizations).build();

    doThrow(new MailgunException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR)).when(mailgunServiceClient).sendEmail(anyMap());

    ResponseEntity result = mailgunServiceFallback.sendEmail(sendGridRequest);
  }

  private List<Content> buildContent(String body) {
    Content content = Content.builder().type("play/text").value(body).build();
    List<Content> contents = new ArrayList<>();
    contents.add(content);
    return contents;
  }

  private List<Email> buildEMailList(String email) {
    List<Email> emailList = new ArrayList<>();
    emailList.add(Email.builder().email(email).build());
    return emailList;
  }

}

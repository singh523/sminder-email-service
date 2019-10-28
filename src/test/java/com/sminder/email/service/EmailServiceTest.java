package com.sminder.email.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.sminder.email.clients.exception.SendGridException;
import com.sminder.email.clients.sendgrid.SendGridServiceClient;
import com.sminder.email.clients.sendgrid.model.SendGridRequest;
import com.sminder.email.clients.sendgrid.model.SendGridResponse;
import com.sminder.email.model.EmailRequest;
import com.sminder.email.model.EmailResponse;
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
public class EmailServiceTest {

  private EmailService emailService;

  @Mock
  private SendGridServiceClient sendGridServiceClient;

  @Before
  public void setup() {
    emailService = new EmailService(sendGridServiceClient);
  }

  @Test
  public void sendEmail_should_successful_for_validdata() {
    List<String> emailList = new ArrayList<>();
    emailList.add("test1@gtest.com");
    EmailRequest emailRequest = EmailRequest.builder().from("test@test.com").subject("email subject").body("email body").toRecipients(emailList).build();

    SendGridResponse sendGridResponse = SendGridResponse.builder().message("Message accepted successfully").status(HttpStatus.OK).build();
    ResponseEntity responseEntity = new ResponseEntity<>(sendGridResponse, HttpStatus.OK);
    when(sendGridServiceClient.sendEmail(any(SendGridRequest.class))).thenReturn(responseEntity);

    EmailResponse response = emailService.sendEmail(emailRequest);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatus());
    assertEquals("Message accepted successfully", response.getMessage());
  }

  @Test(expected = SendGridException.class)
  public void sendEmail_should_throwException_for_clientexception() {
    List<String> emailList = new ArrayList<>();
    emailList.add("test1@gtest.com");
    EmailRequest emailRequest = EmailRequest.builder().from("test@test.com").subject("email subject").body("email body").toRecipients(emailList).build();

    SendGridResponse sendGridResponse = SendGridResponse.builder().build();
    ResponseEntity responseEntity = new ResponseEntity<>(sendGridResponse, HttpStatus.OK);
    doThrow(new SendGridException("Internal Server error", HttpStatus.INTERNAL_SERVER_ERROR)).when(sendGridServiceClient)
        .sendEmail(any(SendGridRequest.class));

    EmailResponse response = emailService.sendEmail(emailRequest);

  }

}

package com.company.email.api;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.company.email.ApplicationIT;
import com.company.email.model.EmailRequest;
import com.company.email.model.EmailResponse;
import com.company.email.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class EmailControllerIT extends ApplicationIT {

  @MockBean
  private EmailService emailService;

  @Autowired
  private ObjectMapper objectMapper;

  private final String path = "/email/send";

  @Test
  public void emailSend_should_successful_for_validdata_and_return_ok() throws Exception {

    EmailRequest emailRequest = buildEmailRequest("test@test.com", "email body");
    EmailResponse emailResponse = EmailResponse.builder().status(HttpStatus.OK).message("Message accepted successfully").build();

    when(emailService.sendEmail(emailRequest)).thenReturn(emailResponse);
    String json = objectMapper.writeValueAsString(emailRequest);

    mockMvc.perform(post(path).content(json).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is("Message accepted successfully")));

  }

  @Test
  public void emailsend_should_fail_for_invaliddata_and_return_badrequest() throws Exception {
    EmailRequest emailRequest = buildEmailRequest("testtest.com", "email body");
    EmailResponse emailResponse = EmailResponse.builder().status(HttpStatus.BAD_REQUEST).message("BAD_REQUEST").build();

    when(emailService.sendEmail(emailRequest)).thenReturn(emailResponse);
    String json = objectMapper.writeValueAsString(emailRequest);

    mockMvc.perform(post(path).content(json).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print()).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.details[0]", is("from: must be a well-formed email address")));

  }

  @Test
  public void emailsend_should_fail_for_emptybody_and_return_badrequest() throws Exception {
    EmailRequest emailRequest = buildEmailRequest("test@test.com", "");
    EmailResponse emailResponse = EmailResponse.builder().status(HttpStatus.BAD_REQUEST).message("BAD_REQUEST").build();

    when(emailService.sendEmail(emailRequest)).thenReturn(emailResponse);
    String json = objectMapper.writeValueAsString(emailRequest);

    mockMvc.perform(post(path).content(json).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print()).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.details[0]", is("body: must not be blank")));

  }

  private EmailRequest buildEmailRequest(String email, String body) {
    List emailList = new ArrayList<>();
    emailList.add(email);
    return EmailRequest.builder().from(email).body(body).subject("email subject").toRecipients(emailList).build();
  }

}

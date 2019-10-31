package com.company.email.clients.mailgun;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.company.email.clients.ClientUtil;
import com.company.email.clients.exception.MailgunException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import java.util.Collection;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

@RunWith(MockitoJUnitRunner.class)
public class MailgunErrorDecoderTest {

  private MailgunErrorDecoder errorDecoder;

  @Spy
  ObjectMapper objectMapper;

  private String methodKey = "test";


  @Before
  public void setUp() throws Exception {
    errorDecoder = new MailgunErrorDecoder(objectMapper);
  }

  @Test
  public void should_decode_4xx_error_and_return_MailgunException() {
    final String rawError = "{\"message\": \"Mailgun client specific error\"}";

    try {
      Response response = Response.builder().status(400).reason("BAD_REQUEST").headers(Collections.<String, Collection<String>>emptyMap())
          .body(ClientUtil.getBody(rawError)).build();
      errorDecoder.decode(methodKey, response);
    } catch (MailgunException e) {
      assertNotNull(e);
      assertEquals(HttpStatus.BAD_REQUEST, e.getHttpStatus());
    }

  }

  @Test
  public void should_decode_5xx_error_and_return_MailgunException() {
    final String rawError = "{\"message\": \"Internal server error\"}";

    try {
      Response response = Response.builder().status(500).reason("INTERNAL_SERVER_ERROR").headers(Collections.<String, Collection<String>>emptyMap())
          .body(ClientUtil.getBody(rawError)).build();
      errorDecoder.decode(methodKey, response);
    } catch (MailgunException e) {
      assertNotNull(e);
      assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getHttpStatus());
    }

  }
}

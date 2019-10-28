package com.sminder.email.clients.sendgrid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.sminder.email.clients.ClientUtil;
import com.sminder.email.clients.exception.SendGridException;
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
public class SendGridErrorDecoderTest {

  private SendGridErrorDecoder errorDecoder;

  private String methodKey = "test";

  @Before
  public void setUp() throws Exception {
    errorDecoder = new SendGridErrorDecoder(objectMapper);
  }

  @Spy
  private ObjectMapper objectMapper;

  @Test
  public void should_decode_4xx_error_and_throw_SendgridException() {
    final String rawError = "{\"status\": \"BAD_REQUEST\", \"message\": \"Invalid input details\", \"errors\": [{\"message\": \"invalid input details in cc field\", \"field\": \"field\",\"help\":\"help message\"}]}";

    try {
      Response response = Response.builder().status(400).reason("BAD_REQUEST").headers(Collections.<String, Collection<String>>emptyMap())
          .body(ClientUtil.getBody(rawError)).build();
      errorDecoder.decode(methodKey, response);
    } catch (SendGridException e) {
      assertNotNull(e);
      assertEquals(HttpStatus.BAD_REQUEST, e.getHttpStatus());
      assertEquals("Invalid input details", e.getErrorMessage());
    }

  }

  @Test
  public void should_decode_5xx_error_and_throw_SendgridException() {
    final String rawError = "{\"message\": \"Internal server error\"}";

    try {
      Response response = Response.builder().status(500).reason("INTERNAL_SERVER_ERROR").headers(Collections.<String, Collection<String>>emptyMap())
          .body(ClientUtil.getBody(rawError)).build();
      errorDecoder.decode(methodKey, response);
    } catch (SendGridException e) {
      assertNotNull(e);
      assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getHttpStatus());
    }

  }


}

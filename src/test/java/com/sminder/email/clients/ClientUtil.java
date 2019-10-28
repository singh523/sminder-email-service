package com.sminder.email.clients;

import feign.Response.Body;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.StringUtils;

public class ClientUtil {

  public static Body getBody(final String bodyString) {
    return new Body() {
      @Override
      public Integer length() {
        return bodyString.length();
      }

      @Override
      public boolean isRepeatable() {
        return false;
      }

      @Override
      public InputStream asInputStream() throws IOException {
        if (StringUtils.isNotBlank(bodyString)) {
          return new ByteArrayInputStream(bodyString.getBytes(StandardCharsets.UTF_8));
        }
        return null;
      }

      @Override
      public Reader asReader() throws IOException {
        if (StringUtils.isNotBlank(bodyString)) {
          return new StringReader(bodyString);
        }
        return null;
      }

      @Override
      public void close() throws IOException {

      }
    };
  }

}

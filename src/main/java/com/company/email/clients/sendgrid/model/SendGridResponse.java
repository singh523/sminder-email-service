package com.company.email.clients.sendgrid.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Builder
public class SendGridResponse implements Serializable {

  @JsonProperty
  private HttpStatus status;

  @JsonProperty
  private String message;

  @JsonProperty
  private List<ErrorDetails> errors;

  @JsonCreator
  SendGridResponse(@JsonProperty("message") String message, @JsonProperty("status") HttpStatus status) {
    this.status = status;
    this.message = message;
  }

  @JsonCreator
  SendGridResponse(@JsonProperty("message") String message) {
    this.message = message;
  }
}



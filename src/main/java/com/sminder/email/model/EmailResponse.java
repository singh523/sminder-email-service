package com.sminder.email.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class EmailResponse {

  private HttpStatus status;
  private String message;
  private List<String> errors;

  @JsonCreator
  EmailResponse(@JsonProperty("status") HttpStatus status, @JsonProperty("message") String message, @JsonProperty("errors") List<String> errors) {
    this.status = status;
    this.message = message;
    this.errors = errors;
  }
}

package com.company.email.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@Data
@Builder
@EqualsAndHashCode
@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder({"status", "message", "details"})
public class EmailServiceException implements Serializable {

  @JsonProperty
  private int status;

  @JsonProperty
  private HttpStatus message;

  @JsonProperty
  private List<String> details;
}

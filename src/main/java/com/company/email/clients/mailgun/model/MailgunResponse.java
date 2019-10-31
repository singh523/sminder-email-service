package com.company.email.clients.mailgun.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
@AllArgsConstructor
@Builder
public class MailgunResponse {

  @JsonProperty
  private String id;
  @JsonProperty
  private String message;


  @JsonCreator
  MailgunResponse(@JsonProperty("message") String message) {
    this.message = message;
  }

}

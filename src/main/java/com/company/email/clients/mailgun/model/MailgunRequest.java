package com.company.email.clients.mailgun.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class MailgunRequest {

  @JsonProperty
  private String from;
  @JsonProperty
  private String to;
  @JsonProperty
  private String text;
  @JsonProperty
  private String subject;
  @JsonProperty
  private String bcc;
  @JsonProperty
  private String cc;


}

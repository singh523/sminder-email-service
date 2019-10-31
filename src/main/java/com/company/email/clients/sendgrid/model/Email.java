package com.company.email.clients.sendgrid.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@Validated
public class Email {

  @javax.validation.constraints.Email
  @JsonProperty
  private String email;

}

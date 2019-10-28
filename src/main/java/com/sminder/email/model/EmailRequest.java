package com.sminder.email.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Getter
@Builder
@JsonPropertyOrder({"from", "to", "bcc", "cc", "subject", "body"})
@JsonInclude(Include.NON_NULL)
public class EmailRequest {


  @Email
  @NotBlank
  @JsonProperty
  private String from;

  @NotNull
  @JsonProperty
  private List<String> toRecipients;

  @JsonProperty
  private List<String> bccRecipients;

  @JsonProperty
  private List<String> ccRecipients;

  @NotBlank
  @JsonProperty
  private String subject;

  @NotBlank
  @JsonProperty
  private String body;

}

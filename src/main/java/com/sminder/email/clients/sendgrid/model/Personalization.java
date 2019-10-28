package com.sminder.email.clients.sendgrid.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class Personalization {

  @Valid
  @JsonProperty("to")
  private List<Email> tos;

  @Valid
  @JsonProperty("cc")
  private List<Email> ccs;

  @Valid
  @JsonProperty("bcc")
  private List<Email> bccs;

  @NotBlank
  private String subject;

}

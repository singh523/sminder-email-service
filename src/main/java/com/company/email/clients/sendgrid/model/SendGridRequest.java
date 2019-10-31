package com.company.email.clients.sendgrid.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Data
@Builder
@Getter
@EqualsAndHashCode
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SendGridRequest {

  @Valid
  @NotNull
  @JsonProperty("personalizations")
  private List<Personalization> personalizations;

  @NotNull
  @JsonProperty("content")
  private List<Content> content;

  @NotBlank
  @JsonProperty("from")
  private Email from;
}

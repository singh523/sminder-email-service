package com.company.email.api;

import com.company.email.model.EmailRequest;
import com.company.email.model.EmailResponse;
import com.company.email.service.EmailService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@Validated
public class EmailController {

  private EmailService emailService;

  @Autowired
  EmailController(@NotNull EmailService emailService) {
    this.emailService = emailService;
  }

  @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity sendEmail(@Valid @RequestBody EmailRequest emailRequest) {
    EmailResponse emailResponse = emailService.sendEmail(emailRequest);
    return ResponseEntity.ok(emailResponse);
  }
}

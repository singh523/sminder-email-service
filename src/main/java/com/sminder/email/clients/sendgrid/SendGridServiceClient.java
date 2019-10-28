package com.sminder.email.clients.sendgrid;

import com.sminder.email.clients.mailgun.MailgunServiceFallback;
import com.sminder.email.clients.sendgrid.model.SendGridRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "sendgrid-service", url = "${email.sendgrid-service.url}", configuration = SendGridConfiguration.class, fallback = MailgunServiceFallback.class)
@Qualifier("sendgrid-service")
public interface SendGridServiceClient {

  @RequestMapping(method = RequestMethod.GET, value = "${email.sendgrid-service.path}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity sendEmail(@Valid SendGridRequest sendGridRequest);

}

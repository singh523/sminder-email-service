package com.company.email.clients.mailgun;

import com.company.email.clients.mailgun.model.MailgunResponse;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mailgun-service", url = "${email.mailgun-service.url}", configuration = MailgunConfiguration.class)
public interface MailgunServiceClient {

  @RequestMapping(method = RequestMethod.POST, value = "${email.mailgun-service.path}", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  MailgunResponse sendEmail(@RequestParam Map<String, ?> request);

}

package com.sminder.email.clients.mailgun;

import com.sminder.email.clients.mailgun.model.MailgunResponse;
import com.sminder.email.clients.sendgrid.SendGridServiceClient;
import com.sminder.email.clients.sendgrid.model.Email;
import com.sminder.email.clients.sendgrid.model.SendGridRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailgunServiceFallback implements SendGridServiceClient {

  private MailgunServiceClient mailgunServiceClient;

  @Autowired
  MailgunServiceFallback(final MailgunServiceClient mailgunServiceClient) {
    this.mailgunServiceClient = mailgunServiceClient;
  }

  @Override
  public ResponseEntity sendEmail(SendGridRequest sendGridRequest) {
    log.info("MailgunServiceFallback method invoked");
    MailgunResponse mailgunResponse = mailgunServiceClient.sendEmail(buildMailgunRequest(sendGridRequest));
    log.debug("Mailgunresponse message :" + mailgunResponse.getMessage());
    return new ResponseEntity("Queued. Thank you.".equals(mailgunResponse.getMessage()) ? HttpStatus.ACCEPTED : HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private Map<String, ?> buildMailgunRequest(SendGridRequest sendGridRequest) {
    Map<String, String> formParamsMap = new HashMap<>();
    formParamsMap.put("from", sendGridRequest.getFrom().getEmail());
    formParamsMap.put("subject", sendGridRequest.getPersonalizations().get(0).getSubject());
    formParamsMap.put("text", sendGridRequest.getContent().get(0).getValue());
    return updateEmailAddress(formParamsMap, sendGridRequest);
  }

  private Map<String, String> updateEmailAddress(Map<String, String> formParamsMap, SendGridRequest sendGridRequest) {

    emailsWithCSV(formParamsMap, sendGridRequest.getPersonalizations().get(0).getTos(), "to");
    emailsWithCSV(formParamsMap, sendGridRequest.getPersonalizations().get(0).getCcs(), "cc");
    emailsWithCSV(formParamsMap, sendGridRequest.getPersonalizations().get(0).getBccs(), "bcc");

    return formParamsMap;
  }

  private Map<String, String> emailsWithCSV(Map<String, String> formParamsMap, List<Email> list, String mailType) {
    String mailIds = list != null && list.size() > 0 ? list.stream().map(x -> x.getEmail()).collect(Collectors.joining(",")) : "";
    if (mailIds.length() > 0) {
      formParamsMap.put(mailType, mailIds);
    }
    return formParamsMap;
  }

}

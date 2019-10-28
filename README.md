# sminder-email-service

## Overview
The objective of this service to create a RESTful service that accepts the necessary information and sends an emails with the Mailgun or Sendgrid api providers. The service implementation is an abstraction between two different email service providers. If one service goes down, the backend service will failover to a other mail service provider without affecting the customers. This solution will cater for multiple email recipients, CCs and BCCs.

## Implementation

Sminder email service has been implemented as a Micro service with Spring boot & java8. Have used spring feign to call mail service providers.

## How to call API

Micro service has been deployed to AWS Elastic Beanstalk and accessible with the below URL

http://EmailService-prd.59mw7z5pp9.us-east-2.elasticbeanstalk.com/email/send

Please ensure you are passing content type as application/json

Content-Type - application/json

#### Request format:
The API has only one RESTful endpoint for sending an emails (/email/send). The endpoint accepts only POST method with content-type as JSON (application/json). The request has the following fields:


| Field name   | Type    | Optional|
| ------------ |:-------:|:-------:|
| from         | String  |    N    |
| to           | List    |    N    |
| cc           | List    |    Y    |
| bcc          | List    |    Y    |
| subject      | String  |    N    |
| body         | String  |    N    |

A sample JSON request is as follows:

```json
{
	"from": "dontreploy@gmail.com",
    "toRecipients": ["singh523@gmail.com"],
	"body" : "body of email",
	"subject": "Subject of email",
	"test":"asdf"
}
```

A sample JSON response is as follows:

```json
{
    "status": "ACCEPTED",
    "message": "Message accepted successfully"
}
```

A sample JSON response for invalid input request

```json
{
    "status": 400,
    "message": "BAD_REQUEST",
    "details": [
        "from: must be a well-formed email address",
        "toRecipients: must not be null"
    ]
}
```
When Fallback service fails 
```json
{
    "status": 500,
    "message": "INTERNAL_SERVER_ERROR",
    "details": [
        "SendGridServiceClient#sendEmail(SendGridRequest) failed and fallback failed."
    ]
}
```

## How to run in local

##### Prerequisites
      - Git
      - Java 8
      - Ensure `JAVA_HOME` is set
      
##### Instructions
     1. Clone repository
        `git clone <REPOSITORY_URL> .`
        
    2. Update the following environment variables required for the application to run in application.yml under resources folder.
             - email:
                  sendgrid-service:
                    token: Bearer <token>
                  mailgun-service:
                    path: /v3/<mailgun-domain>/messages
                    username: api
                    password: <password>
     
     3. Navigate to the project location and build the project
        `cd sminder-email-service`
        `mvn clean install`
      4. Starting the application
        Assuming you are in the project root directory
        `mvn spring-boot:run`

### Todo
   - Updating with proper exception messages/status codes for the REST controllers based on UI requirement.
   - Improvement in logging & updating few more test cases to cover other scenarios as part of unit testing.
   - Integration of swagger 

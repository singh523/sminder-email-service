# sminder-email-service

## Overview
The objective of this service to create a Springboot Micro service that accepts the necessary information and sends an emails with the Mailgun or Sendgrid api providers. The service implementation is an abstraction between two different email service providers. If one service goes down, the backend service will failover to a other mail service provider without affecting the customers. This solution will cater for multiple email recipients, CCs and BCCs.

## Implementation

Email service has been implemented as a Micro service with Spring boot & java8. Have used spring feign to call mail service providers.

## How to call API

Please ensure you are passing content type as application/json

Content-Type - application/json

#### Request format:
The API has only one RESTful endpoint for sending an emails (/email/send). The endpoint accepts only POST method with content-type as JSON (application/json). The request has the following fields:


| Field name   | Type    | Optional|
| ------------ |:-------:|:-------:|
| from         | String  |    N    |
| toRecipients | List    |    N    |
| ccRecipients | List    |    Y    |
| bccRecipients| List    |    Y    |
| subject      | String  |    N    |
| body         | String  |    N    |

A sample JSON request is as follows:

```json
{
	"from": "dontreploy@gmail.com",
    "toRecipients": ["test2@gmail.com"],
    "ccRecipients": ["test3@gmail.com"],
    "bccRecipients": ["test4@gmail.com"],
	"body" : "Body of the email",
	"subject": "Subject of the email"
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
        "Exception occurred in processing your request, please try again!"
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
        `cd springboot-email-service`
        `mvn clean install`
      4. Starting the application
        Assuming you are in the project root directory
        `mvn spring-boot:run`

### Todo
   - Updating with proper exception messages/status codes for the REST controllers based on UI requirement.
   - Improvement in logging & updating few more test cases to cover other scenarios as part of unit testing.
   - Integration of swagger 

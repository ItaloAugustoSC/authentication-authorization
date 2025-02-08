package com.italoasc.authentication_authorization.networking;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SesEmailSender implements EmailSenderGateway {

//    @Value("${aws.region}")
//    private static String AWS_REGION;

    private AmazonSimpleEmailService amazonSimpleEmailService;

    @Autowired
    public SesEmailSender(AmazonSimpleEmailService amazonSimpleEmailService) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        SendEmailRequest request = new SendEmailRequest(
                "italo.augusto.ct@gmail.com",
                new Destination(Collections.singletonList(to)),
                new Message(new Content(subject), new Body(new Content(body))));

        amazonSimpleEmailService.sendEmail(request);
    }
}

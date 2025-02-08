package com.italoasc.authentication_authorization.networking;

public interface EmailSenderGateway {
    void sendEmail(String to, String subject, String body);
}

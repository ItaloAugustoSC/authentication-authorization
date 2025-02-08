package com.italoasc.authentication_authorization.service;

import com.italoasc.authentication_authorization.entity.User;
import com.italoasc.authentication_authorization.networking.EmailSenderGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class MfaService {
    private final Map<String, String> otpStorage = new HashMap<>();
    private final SecureRandom random = new SecureRandom();
    private final EmailSenderGateway emailSenderGateway;

    @Autowired
    public MfaService(EmailSenderGateway emailSenderGateway) {
        this.emailSenderGateway = emailSenderGateway;
    }

    public void generateAndSendMfaCode(User user) {
        String otp = String.valueOf(100000 + random.nextInt(900000)); // Gera um código de 6 dígitos
        otpStorage.put(user.getUsername(), otp);
        // TODO: add cache to save the otp temporarily
        if(isMfaEmailType(user.getMfaType())){
            //Email hardcoded por naõ ser permitido envio para outros emails em conta teste AWS
           emailSenderGateway.sendEmail("italo.augusto.ct@gmail.com", "MFA code", "Your MFA code is: " + otp);
        } else if (isMfaSmsType(user.getMfaType())) {
            // TODO: add method to send MFA via sms (Twilio or AWS SES)
        }
    }

    public boolean verifyMfaCode(String username, String otp) {
        String storedOtp = otpStorage.get(username);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStorage.remove(username);
            return true;
        }
        return false;
    }

    private boolean isMfaEmailType(String mfaType){
        return mfaType.equalsIgnoreCase("email");
    }

    private boolean isMfaSmsType(String mfaType){
        return mfaType.equalsIgnoreCase("SMS");
    }
}

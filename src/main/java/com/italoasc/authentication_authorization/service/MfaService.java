package com.italoasc.authentication_authorization.service;

import com.italoasc.authentication_authorization.entity.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class MfaService {
    private final Map<String, String> otpStorage = new HashMap<>();
    private final SecureRandom random = new SecureRandom();

    public void generateAndSendMfaCode(User user) {
        String otp = String.valueOf(100000 + random.nextInt(900000)); // Gera um código de 6 dígitos
        otpStorage.put(user.getUsername(), otp);
        System.out.printf(otp);
        // TODO: add cache to save the otp temporarily
        if(isMfaEmailType(user.getMfaType())){
            // TODO: add method to send MFA via email (Twilio or AWS SES)
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

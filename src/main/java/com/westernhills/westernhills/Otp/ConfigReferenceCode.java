package com.westernhills.westernhills.Otp;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;




@Component
public class ConfigReferenceCode {


    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String generateCouponCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();
        int length=10;

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            code.append(randomChar);


            if ((i + 1) % 4 == 0 && (i + 1) < length) {
                code.append("/");
            }
        }

        return code.toString();
    }





}

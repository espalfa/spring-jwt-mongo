package com.logistic.app.app.utils;

import java.security.SecureRandom;
import java.util.Random;

public class AlphaNumericUtil {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";

    public String generateRandomString(int length) {
        StringBuffer buffer = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            buffer.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(buffer);
    }

    public boolean checkIfStringExits(String trackingNum) {
        return true;
    }
}

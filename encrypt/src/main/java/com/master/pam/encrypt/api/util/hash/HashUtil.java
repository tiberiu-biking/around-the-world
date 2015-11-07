package com.master.pam.encrypt.api.util.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    private static final String SALT = "secret:salt";

    public static String getHash(String aMessage) {
        return generateHash(SALT + aMessage);
    }

    private static String generateHash(String aMessage) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = sha.digest(aMessage.getBytes());
            char[] digits = {'*', '~', '2', 'x', '4', '9', 'e', 'r', 's', 'e', '!', '7', 'w', '6', '0', '_'};
            for (byte b : hashedBytes) {
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
        }
        return hash.toString();
    }

}

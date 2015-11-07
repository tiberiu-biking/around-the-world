package com.master.pam.encrypt.api;

import com.master.pam.encrypt.api.util.encrypt.AESCipher;
import com.master.pam.encrypt.api.util.encrypt.KeystoreUtil;
import com.master.pam.encrypt.api.util.hash.HashUtil;

import java.security.Key;

public class EncryptApi implements IEncryptApi {

    private static final String KEYSTORE_LOCATION = "src/test/resources/aes-keystore.jck";
    private static final String STORE_PASS = "mystorepass";
    private static final String ALIAS = "jceksaes";
    private static final String KEY_PASS = "mykeypass";
    private static final String INITIAL_V = "4000297891200071";
    private Key keyFromKeyStore;

    private Key getKeyFromKeyStore() {
        if (keyFromKeyStore == null)
            keyFromKeyStore = KeystoreUtil.getKeyFromKeyStore(KEYSTORE_LOCATION, STORE_PASS, ALIAS, KEY_PASS);
        return keyFromKeyStore;
    }

    @Override
    public String encrpyt(String aMessage) {
        AESCipher cipher = new AESCipher(getKeyFromKeyStore(), INITIAL_V.getBytes());
        String result = cipher.getEncryptedMessage(aMessage);
        return result;
    }

    @Override
    public String decrpyt(String aMessage) {
        AESCipher cipher = new AESCipher(getKeyFromKeyStore(), INITIAL_V.getBytes());
        String result = cipher.getDecryptedMessage(aMessage);
        return result;
    }

    @Override
    public String hash(String aMessage) {
        return HashUtil.getHash(aMessage);
    }

}

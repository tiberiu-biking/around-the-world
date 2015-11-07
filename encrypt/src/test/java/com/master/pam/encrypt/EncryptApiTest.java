package com.master.pam.encrypt;

import com.master.pam.encrypt.api.util.hash.HashUtil;
import org.junit.Test;

public class EncryptApiTest {

    @Test
    public void encryptMessageTest() {
        // System.out.println( "Password decypted = " + EncryptApi.API.encrpyt(
        // "password" ) );
    }

    @Test
    public void decryptMessageTest() {
        // System.out.println( "Password decrypted = " + EncryptApi.API.decrpyt(
        // "H0lxmJwCXVmSRxFgW71CWw==" ) );
    }

    @Test
    public void hashTibisPassword() {
        System.out.println("Tibi's password hashed = " + HashUtil.getHash("x"));
    }

    @Test
    public void hashAlesPassword() {
        System.out.println("Ale's password hashed = " + HashUtil.getHash("a"));
    }

}

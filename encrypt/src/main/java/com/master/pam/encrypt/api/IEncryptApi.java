package com.master.pam.encrypt.api;

public interface IEncryptApi {

    String encrpyt(String aMessage);

    String decrpyt(String aMessage);

    String hash(String aMessage);

}

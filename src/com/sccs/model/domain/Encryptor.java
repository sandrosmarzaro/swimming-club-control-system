package com.sccs.model.domain;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

public class Encryptor {

    public static String encryptPassword(String password) {

        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm("md5");
        passwordEncryptor.setPlainDigest(true);
        return passwordEncryptor.encryptPassword(password);
    }

    public static Boolean isCorrectPassword(String password, String encryptedPassword) {

        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm("md5");
        passwordEncryptor.setPlainDigest(true);
        return passwordEncryptor.checkPassword(encryptedPassword, password);
    }
}

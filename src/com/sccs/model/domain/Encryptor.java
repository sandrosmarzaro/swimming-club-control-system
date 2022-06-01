package com.sccs.model.domain;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

public class Encryptor {
    
    private static ConfigurablePasswordEncryptor generateEncryptor() {
        
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm("SHA-256");
        passwordEncryptor.setPlainDigest(true);
        return passwordEncryptor;
    }
    
    public static String encryptPassword(String password) {
        
        return generateEncryptor().encryptPassword(password);
    }

    public static Boolean isCorrectPassword(String password, String encryptedPassword) {
        
        return generateEncryptor().checkPassword(password, encryptedPassword);
    }
}

package com.safety_signature.safety_signature_back.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Configuration
@EnableEncryptableProperties
class JasyptConfigTest {

    @Test
    public void encryptDecryptTest() {
        String text = "changeIt";//변경할 값

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("비밀키");
        encryptor.setAlgorithm("PBEWithMD5AndDES");


        String encryptedText = encryptor.encrypt(text);
        System.out.println("enc : " + encryptedText);

        String decryptedText = encryptor.decrypt(encryptedText);
        System.out.println("dec : " + decryptedText);

        assertEquals(text, decryptedText);

        List.of("")
            .stream()
            .forEach(s -> {
                System.out.println(s + " -> ENC(" + encryptor.encrypt(s)+")");
            });
    }
}
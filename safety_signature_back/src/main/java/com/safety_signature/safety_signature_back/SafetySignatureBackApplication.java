package com.safety_signature.safety_signature_back;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableEncryptableProperties
public class SafetySignatureBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SafetySignatureBackApplication.class, args);
    }

}

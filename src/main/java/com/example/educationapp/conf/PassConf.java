package com.example.educationapp.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PassConf {

    @Bean
    public PasswordEncoder passEnc(){
        return new BCryptPasswordEncoder(10);
    }
}

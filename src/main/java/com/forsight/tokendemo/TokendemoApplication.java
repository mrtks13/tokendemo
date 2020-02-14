package com.forsight.tokendemo;

import com.forsight.tokendemo.util.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TokendemoApplication {

    @Autowired
    @Qualifier("applicationContext")
    ApplicationContextProvider applicationContextProvider;

    public static void main(String[] args) {
        SpringApplication.run(TokendemoApplication.class, args);
    }

}

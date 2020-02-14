package com.forsight.tokendemo;

import com.forsight.tokendemo.util.ApplicationContextProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TokendemoApplicationTests {
    @Autowired
    @Qualifier("applicationContext")
    ApplicationContextProvider applicationContextProvider;

    @Test
    void contextLoads() {
    }

}

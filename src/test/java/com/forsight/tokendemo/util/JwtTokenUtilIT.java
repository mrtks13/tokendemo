package com.forsight.tokendemo.util;

import com.forsight.tokendemo.exception.TokenDemoException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTokenUtilIT {


    @Before
    public void setUp() {

    }


    @Test
    public void generateToken_WhenTwoHour_Then_OK() {
        //Given
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil("4dsight", 432000L);
        String userName = "murat";

        //when
        String token = jwtTokenUtil.generateToken("murat");

        //then
        assertEquals("ey", token.substring(0, 2));
    }

    @Test
    public void getUsernameFromToken_WhenUserName_OK() {
        //Given
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil("4dsight", 432000L);
        String username = "muratakkus";
        String token = jwtTokenUtil.generateToken("muratakkus");

        //then
        String result = jwtTokenUtil.getUsernameFromToken(token);

        //when

        assertEquals("username is not match", username, result);

    }

    @Test(expected = TokenDemoException.class)
    public void isTokenExpired_OK() throws InterruptedException {
        //Given
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil("4dsight", 5L);

        String token = jwtTokenUtil.generateToken("muratakkus");
        Thread.sleep(6000);

        //when
        Boolean result = jwtTokenUtil.isTokenExpired(token);
        //then
        assertEquals(true, result);

    }


}
package com.forsight.tokendemo.controller;

import com.forsight.tokendemo.dto.request.ChangePasswordRequestDto;
import com.forsight.tokendemo.dto.response.ChangePasswordResponseDto;
import com.forsight.tokendemo.util.JwtTokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

public class UserControllerTest extends AbstractTest {


    @Override
    @Before
    public void setUp() {
        super.setUp();
    }


    String token;
    String URL;

    @Test
    public void sendUrl_OK() throws Exception {

        String uri = "/users/token";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String url = mvcResult.getResponse().getContentAsString();
        URL = url;
        // token = super.mapFromJson(content, String.class);
        assertEquals(true, url.contains("http://localhost:80/users/newspassword?id="));

    }

    @Test
    public void validateToken() throws Exception {

        String uri = "/users/token";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String url = mvcResult.getResponse().getContentAsString();
        // token = super.mapFromJson(content, String.class);
        assertEquals(true, url.contains("http://localhost:80/users/newspassword?id="));

        uri = url;
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ChangePasswordResponseDto changePasswordResponseDto = super.mapFromJson(content, ChangePasswordResponseDto.class);

        assertEquals("5e1cb3a8c68777680f93aa40", changePasswordResponseDto.getUserId());
    }

    @Test
    public void changePassword() throws Exception {

        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil("4dsight", 432000L);
        String username = "muratakkus";
        String token = jwtTokenUtil.generateToken(username);

        ChangePasswordRequestDto changePasswordRequestDto = new ChangePasswordRequestDto();
        changePasswordRequestDto.setNewPassword("Aa123456");
        changePasswordRequestDto.setNewPasswordAgain("Aa123456");
        changePasswordRequestDto.setUserId("5e1cb3a8c68777680f93aa40");
        String content = super.mapToJson(changePasswordRequestDto);

        System.out.println(token);

        System.out.println(content);

        String uri = "/users/changepassword";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization","Bearer ".concat(token));
        httpHeaders.add("Content-Type","application/json;charset=UTF-8;");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                //.accept(MediaType.APPLICATION_JSON_VALUE)
                .headers(httpHeaders)
                .content(content)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }
}
package com.forsight.tokendemo.controller;

import com.forsight.tokendemo.dto.request.ChangePasswordRequestDto;
import com.forsight.tokendemo.dto.response.ChangePasswordResponseDto;
import com.forsight.tokendemo.util.JwtTokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import static org.junit.Assert.assertEquals;

public class UserControllerTest extends AbstractTest {


    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void sendUrl_OK() throws Exception {

        String uri = "/users/token";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String url = mvcResult.getResponse().getContentAsString();

        Boolean actual = url.contains("http://localhost:80/users/newspassword?id=");

        assertEquals(true, actual);

    }

    @Test
    public void validateToken_OK() throws Exception {

        String uri = "/users/token";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String url = mvcResult.getResponse().getContentAsString();


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
    @WithMockUser(username = "muratakkus", password = "Aa123456", authorities = "CHANGE_PASSWORD_ROLE")
    public void changePassword_OK() throws Exception {

        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil("4dsight", 432000L);
        String username = "muratakkus";
        String token = jwtTokenUtil.generateToken(username);

        ChangePasswordRequestDto changePasswordRequestDto = new ChangePasswordRequestDto();
        changePasswordRequestDto.setNewPassword("Aa123456");
        changePasswordRequestDto.setNewPasswordAgain("Aa123456");
        changePasswordRequestDto.setUserId("5e1cb3a8c68777680f93aa40");
        String content = super.mapToJson(changePasswordRequestDto);

        String uri = "/users/changepassword";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer ".concat(token));
        httpHeaders.add("Content-Type", "application/json;charset=UTF-8;");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .headers(httpHeaders)
                .content(content)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test(expected = NestedServletException.class)
    @WithMockUser(username = "muratakkus", password = "pwd")
    public void changePassword_AccesDenied() throws Exception {

        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil("4dsight", 432000L);
        String username = "muratakkus";
        String token = jwtTokenUtil.generateToken(username);

        ChangePasswordRequestDto changePasswordRequestDto = new ChangePasswordRequestDto();
        changePasswordRequestDto.setNewPassword("Aa123456");
        changePasswordRequestDto.setNewPasswordAgain("Aa123456");
        changePasswordRequestDto.setUserId("5e1cb3a8c68777680f93aa40");
        String content = super.mapToJson(changePasswordRequestDto);

        String uri = "/users/changepassword";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer ".concat(token));
        httpHeaders.add("Content-Type", "application/json;charset=UTF-8;");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .headers(httpHeaders)
                .content(content)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }
}
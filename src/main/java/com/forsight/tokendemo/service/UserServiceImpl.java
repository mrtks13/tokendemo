package com.forsight.tokendemo.service;

import com.forsight.tokendemo.dto.request.ChangePasswordRequestDto;
import com.forsight.tokendemo.dto.response.ChangePasswordResponseDto;
import com.forsight.tokendemo.exception.ExpireTokenException;
import com.forsight.tokendemo.exception.TokenDemoException;
import com.forsight.tokendemo.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import static com.forsight.tokendemo.security.SecurityConstants.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final JwtTokenUtil jwtTokenUtil;


    @Override
    public String generateTokenURL() {
        String userName = "muratakkus";
        String token = jwtTokenUtil.generateToken(userName);

        return TOKEN_URL.concat(token);
    }

    /**
     * validate token
     *
     * @param token
     * @return ChangePasswordResponseDto is for  form initilize
     */
    @Override
    public ChangePasswordResponseDto validateToken(String token, HttpServletResponse response) {
        if (jwtTokenUtil.isTokenExpired(token)) {
            throw new ExpireTokenException();
        }


        //TODO:Check UserService is exist
        String userId = "5e1cb3a8c68777680f93aa40";


        String userName = jwtTokenUtil.getUsernameFromToken(token);

        response.addHeader(AUTHORIZATION_HEADER_NAME, TOKEN_PREFIX.concat(token));

        ChangePasswordResponseDto changePasswordResponseDto = new ChangePasswordResponseDto();

        changePasswordResponseDto.setUserId(userId);

        return changePasswordResponseDto;
    }


    /**
     * change pass word
     *
     * @param changePasswordRequestDto changePassword Form Data
     */
    @Override
    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        log.info("password will be change");
        if (!changePasswordRequestDto.getNewPassword().equals(changePasswordRequestDto.getNewPasswordAgain())) {
            throw new TokenDemoException("Password is not matching");
        }


        //:TODO save passsord service oprations
    }
}

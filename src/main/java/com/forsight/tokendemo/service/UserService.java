package com.forsight.tokendemo.service;

import com.forsight.tokendemo.dto.request.ChangePasswordRequestDto;
import com.forsight.tokendemo.dto.response.ChangePasswordResponseDto;

import javax.servlet.http.HttpServletResponse;

public interface UserService {


    String generateTokenURL();

    ChangePasswordResponseDto validateToken(String token, HttpServletResponse response);

    void changePassword(ChangePasswordRequestDto changePasswordRequestDto);


}

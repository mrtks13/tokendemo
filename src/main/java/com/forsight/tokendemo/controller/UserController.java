package com.forsight.tokendemo.controller;

import com.forsight.tokendemo.dto.request.ChangePasswordRequestDto;
import com.forsight.tokendemo.dto.response.ChangePasswordResponseDto;
import com.forsight.tokendemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/token")
    public ResponseEntity<String> sendUrl(HttpServletRequest request) {

        String appURL = getAppUrl(request);
        String tokenURL = userService.generateTokenURL();
        return new ResponseEntity<>(appURL.concat(tokenURL), HttpStatus.OK);
    }


    @GetMapping(value = "/newspassword")
    public ResponseEntity<ChangePasswordResponseDto> validateToken(@RequestParam("id") String token, HttpServletResponse response) {
        ChangePasswordResponseDto changePasswordResponseDto = userService.validateToken(token, response);
        return new ResponseEntity<>(changePasswordResponseDto, HttpStatus.OK);
    }

    //

    @PostMapping(value = "/changepassword")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CHANGE_PASSWORD_ROLE')")
    public void changePassword(@RequestBody @Valid ChangePasswordRequestDto changePassworRequestDto) {
        userService.changePassword(changePassworRequestDto);
    }


    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}

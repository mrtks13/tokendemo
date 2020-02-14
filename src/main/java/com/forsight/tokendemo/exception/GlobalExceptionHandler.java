package com.forsight.tokendemo.exception;

import com.forsight.tokendemo.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {


    @ExceptionHandler(TokenDemoException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    public ErrorDto handleTokenDemoException(TokenDemoException e) {
        ErrorDto errorOutput = getErrorOutput(e);

        return errorOutput;
    }

    @ExceptionHandler(ExpireTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDto handleTokenDemoException(ExpireTokenException e) {
        ErrorDto errorOutput = getErrorOutput(e);

        return errorOutput;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    public ErrorDto handleAuthenticationException(IllegalArgumentException e) {
        ErrorDto errorOutput = getErrorOutput(e);

        return errorOutput;
    }

    private ErrorDto getErrorOutput(Exception e) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrrorMessage(e.getMessage());
        return errorDto;
    }
}

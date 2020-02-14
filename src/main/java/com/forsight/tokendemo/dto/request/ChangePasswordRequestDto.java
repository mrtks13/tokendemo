package com.forsight.tokendemo.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class ChangePasswordRequestDto implements Serializable {
    private static final long serialVersionUID = 1353168588720099737L;

    @NotEmpty
    private String userId;
    @NotEmpty
    private String newPassword;
    @NotEmpty
    private String newPasswordAgain;


}

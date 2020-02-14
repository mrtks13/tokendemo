package com.forsight.tokendemo.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChangePasswordResponseDto implements Serializable {
    private static final long serialVersionUID = 6897714176814250660L;
    private String userId;

}

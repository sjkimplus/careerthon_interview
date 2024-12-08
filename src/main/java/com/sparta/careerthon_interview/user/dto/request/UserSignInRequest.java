package com.sparta.careerthon_interview.user.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInRequest {
    @Schema(description = "사용자 아이디", example = "BailyK99")
    private String username;
    @Schema(description = "사용자 비밀번호", example = "Password123!")
    private String password;

}

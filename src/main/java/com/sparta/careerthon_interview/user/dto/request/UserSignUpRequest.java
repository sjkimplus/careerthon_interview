package com.sparta.careerthon_interview.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignUpRequest {
    @Schema(description = "사용자 아이디 (자유양식, 단 다른 유저와 중복불가)", example = "BailyK99")
    private String username;
    @Schema(description = "사용자 비밀번호 (8자 이상, 대분자 1자 이상, 소문자 1자 이상, 특수문자 1자 이상)", example = "Password123!")
    private String password;
    @Schema(description = "사용자 별명 (자유양식, 다른 유저와 중복가능)", example = "베일리1004")
    private String nickname;

}

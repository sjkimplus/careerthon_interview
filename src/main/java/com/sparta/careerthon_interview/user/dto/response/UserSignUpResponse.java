package com.sparta.careerthon_interview.user.dto.response;

import com.sparta.careerthon_interview.user.enums.UserRole;
import lombok.Getter;

@Getter
public class UserSignUpResponse {
    private String username;
    private String nickname;
    private UserRole authorities;

    public UserSignUpResponse(String username, String nickname, UserRole authorities) {}
}

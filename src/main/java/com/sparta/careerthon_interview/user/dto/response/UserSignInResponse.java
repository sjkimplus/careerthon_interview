package com.sparta.careerthon_interview.user.dto.response;

import lombok.Getter;

@Getter
public class UserSignInResponse {
    private String token;
    public UserSignInResponse(String token) {
        this.token = token;
    }
}

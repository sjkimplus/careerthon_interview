package com.sparta.careerthon_interview.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignUpRequest {
    private String username;
    private String password;
    private String nickname;

}

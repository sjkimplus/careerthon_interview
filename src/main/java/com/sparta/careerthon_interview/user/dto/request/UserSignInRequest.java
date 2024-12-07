package com.sparta.careerthon_interview.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignInRequest {
    private String username;
    private String password;

}

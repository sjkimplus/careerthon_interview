package com.sparta.careerthon_interview.user.dto.response;

import com.sparta.careerthon_interview.user.entity.User;
import com.sparta.careerthon_interview.user.enums.UserRole;
import lombok.Getter;

import java.util.List;

@Getter
public class UserSignUpResponse {
    private String username;
    private String nickname;
    private List<AuthorityResponse> authorities;

    public UserSignUpResponse(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.authorities = List.of(new AuthorityResponse(user.getRole().name()));
    }

    @Getter
    public static class AuthorityResponse {
        private String authorityName;

        public AuthorityResponse(String authorityName) {
            this.authorityName = authorityName;
        }
    }
}

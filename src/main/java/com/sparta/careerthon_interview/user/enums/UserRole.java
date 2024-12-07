package com.sparta.careerthon_interview.user.enums;

import com.sparta.careerthon_interview.common.exception.CareerthonException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.sparta.careerthon_interview.common.exception.ErrorCode.USER_TYPE_NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    ROLE_USER(Authority.USER);

    private final String role;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new CareerthonException(USER_TYPE_NOT_FOUND));
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
    }
}

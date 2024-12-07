package com.sparta.careerthon_interview.user.controller;

import com.sparta.careerthon_interview.common.dto.ApiResponse;
import com.sparta.careerthon_interview.user.dto.AuthUser;
import com.sparta.careerthon_interview.user.dto.request.UserSignInRequest;
import com.sparta.careerthon_interview.user.dto.request.UserSignUpRequest;
import com.sparta.careerthon_interview.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody UserSignUpRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.signup(request)));
    }

    // 로그인
    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody UserSignInRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.signin(request)));
    }

//    // JWT 테스트용
//    @GetMapping("/check")
//    public ResponseEntity<ApiResponse<?>> check() {
//        return ResponseEntity.ok(ApiResponse.success(userService.check()));
//    }
}

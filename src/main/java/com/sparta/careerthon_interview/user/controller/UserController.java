package com.sparta.careerthon_interview.user.controller;

import com.sparta.careerthon_interview.common.dto.ApiResponse;
import com.sparta.careerthon_interview.user.dto.AuthUser;
import com.sparta.careerthon_interview.user.dto.request.UserSignInRequest;
import com.sparta.careerthon_interview.user.dto.request.UserSignUpRequest;
import com.sparta.careerthon_interview.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name= "유저 컨트롤러", description= "유저 회원가입 및 로그인을 관리하는 API")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "아이디, 비밀번호, 닉네임 값으로 회원가입 진행")
    public ResponseEntity<ApiResponse<?>> create(
            @Parameter(description = "아이디, 비밀번호, 닉네임 값") @RequestBody UserSignUpRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.signup(request)));
    }

    // 로그인
    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "아이디, 비밀번호로 로그인 진행")
    public ResponseEntity<ApiResponse<?>> login(
            @Parameter(description = "아이디, 비밀번호 값")@RequestBody UserSignInRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.signin(request)));
    }
}

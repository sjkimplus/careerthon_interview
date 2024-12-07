package com.sparta.careerthon_interview.user.controller;

import com.sparta.careerthon_interview.common.dto.ApiResponse;
import com.sparta.careerthon_interview.user.dto.request.UserSignInRequest;
import com.sparta.careerthon_interview.user.dto.request.UserSignUpRequest;
import com.sparta.careerthon_interview.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody UserSignUpRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.signup(request)));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody UserSignInRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.signin(request)));
    }

}

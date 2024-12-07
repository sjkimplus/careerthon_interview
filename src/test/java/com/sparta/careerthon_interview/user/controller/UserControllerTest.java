package com.sparta.careerthon_interview.user.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.careerthon_interview.user.dto.request.UserSignInRequest;
import com.sparta.careerthon_interview.user.dto.request.UserSignUpRequest;
import com.sparta.careerthon_interview.user.dto.response.UserSignInResponse;
import com.sparta.careerthon_interview.user.dto.response.UserSignUpResponse;
import com.sparta.careerthon_interview.user.entity.User;
import com.sparta.careerthon_interview.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createUser_success() throws Exception {
        // Given
        UserSignUpRequest request = new UserSignUpRequest("username", "Password1!", "nickname");
        User user = new User(request, "encodedPassword");
        UserSignUpResponse response = new UserSignUpResponse(user);
        when(userService.signup(request)).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/users/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(userService, times(1)).signup(any(UserSignUpRequest.class));
    }

    @Test
    void loginUser_success() throws Exception {
        // Given
        UserSignInRequest request = new UserSignInRequest("username", "Password1!");
        UserSignInResponse response = new UserSignInResponse("jwtToken");
        when(userService.signin(request)).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/users/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}

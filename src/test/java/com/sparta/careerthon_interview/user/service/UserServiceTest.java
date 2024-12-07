package com.sparta.careerthon_interview.user.service;

import com.sparta.careerthon_interview.common.config.JwtUtil;
import com.sparta.careerthon_interview.common.exception.CareerthonException;
import com.sparta.careerthon_interview.user.dto.request.UserSignInRequest;
import com.sparta.careerthon_interview.user.dto.request.UserSignUpRequest;
import com.sparta.careerthon_interview.user.dto.response.UserSignInResponse;
import com.sparta.careerthon_interview.user.dto.response.UserSignUpResponse;
import com.sparta.careerthon_interview.user.entity.User;
import com.sparta.careerthon_interview.user.enums.UserRole;
import com.sparta.careerthon_interview.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.sparta.careerthon_interview.common.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signup_success() {
        // Given
        UserSignUpRequest request = new UserSignUpRequest("username", "Password1!", "nickname");
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        // When
        UserSignUpResponse response = userService.signup(request);

        // Then
        assertEquals("username", response.getUsername());
        assertEquals("nickname", response.getNickname());
        assertEquals(UserRole.ROLE_USER, UserRole.of(response.getAuthorities().get(0).getAuthorityName()));
    }

    @Test
    void signup_fail_missing_format() {
        // Given
        UserSignUpRequest request = new UserSignUpRequest("", "Password1!", "nickname");

        // When & Then
        CareerthonException exception = assertThrows(CareerthonException.class, () -> userService.signup(request));
        assertEquals(MISSING_FORMAT, exception.getErrorCode());
    }

    @Test
    void signup_fail_duplicate_username() {
        // Given
        UserSignUpRequest request = new UserSignUpRequest("username", "Password1!", "nickname");
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(new User()));

        // When & Then
        CareerthonException exception = assertThrows(CareerthonException.class, () -> userService.signup(request));
        assertEquals(USER_ID_DUPLICATION, exception.getErrorCode());
    }

    @Test
    void signup_fail_invalid_password_format() {
        // Given
        UserSignUpRequest request = new UserSignUpRequest("username", "password", "nickname");

        // When & Then
        CareerthonException exception = assertThrows(CareerthonException.class, () -> userService.signup(request));
        assertEquals(WRONG_FORMAT, exception.getErrorCode());
    }

    @Nested
    class SignInTests {
        @BeforeEach
        void setUpUserForLoginTests() {
            // Mock setup logic for signin tests
            UserSignUpRequest request = new UserSignUpRequest("username", "Password1!", "nickname");
            User mockUser = new User(request, "encodedPassword");

            // Simulate repository behavior for finding the user
            when(userRepository.findByUsernameOrThrow("username")).thenReturn(mockUser);

            // Simulate password matching
            when(passwordEncoder.matches("Password1!", "encodedPassword")).thenReturn(true);

            // Simulate JWT token creation
            when(jwtUtil.createToken(mockUser.getId(), mockUser.getUsername(), mockUser.getRole()))
                    .thenReturn("jwtToken");
        }

        @Test
        void signin_success() {
            // Given
            UserSignInRequest request = new UserSignInRequest("username", "Password1!");

            // When
            UserSignInResponse response = userService.signin(request);

            // Then
            assertEquals("jwtToken", response.getToken());
        }

        @Test
        void signin_fail_user_not_found() {
            // Given
            UserSignInRequest request = new UserSignInRequest("wrongUsername", "Password1!");
            when(userRepository.findByUsernameOrThrow(request.getUsername()))
                    .thenThrow(new CareerthonException(USER_NOT_FOUND));

            // When & Then
            CareerthonException exception = assertThrows(CareerthonException.class, () -> userService.signin(request));
            assertEquals(USER_NOT_FOUND, exception.getErrorCode());
        }

        @Test
        void signin_fail_incorrect_password() {
            // Given
            UserSignInRequest request = new UserSignInRequest("username", "wrongPassword1!");

            // When & Then
            CareerthonException exception = assertThrows(CareerthonException.class, () -> userService.signin(request));
            assertEquals(USER_PW_ERROR, exception.getErrorCode());
        }
    }
}


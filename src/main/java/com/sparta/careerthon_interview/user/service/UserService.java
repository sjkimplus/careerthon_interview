package com.sparta.careerthon_interview.user.service;

import com.sparta.careerthon_interview.common.config.JwtUtil;
import com.sparta.careerthon_interview.common.exception.CareerthonException;
import com.sparta.careerthon_interview.user.dto.request.UserSignInRequest;
import com.sparta.careerthon_interview.user.dto.request.UserSignUpRequest;
import com.sparta.careerthon_interview.user.dto.response.UserSignInResponse;
import com.sparta.careerthon_interview.user.dto.response.UserSignUpResponse;
import com.sparta.careerthon_interview.user.entity.User;
import com.sparta.careerthon_interview.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.careerthon_interview.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserSignUpResponse signup(UserSignUpRequest request) {

        // 빈 값이 없는지 확인
        if (request.getUsername().isBlank() || request.getPassword().isBlank() || request.getNickname().isBlank()) {
            throw new CareerthonException(MISSING_FORMAT);
        }

        // 중복 유저 확인
        Optional<User> username = userRepository.findByUsername(request.getUsername());
        if (username.isPresent()) throw new CareerthonException(USER_ID_DUPLICATION);

        // 비밀번호가 패턴에 부합하는지 확인
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!request.getPassword().matches(passwordPattern)) {
            throw new CareerthonException(WRONG_FORMAT); // Throw exception for invalid password format
        }

        // 유저 생성 및 저장
        String password = passwordEncoder.encode(request.getPassword());
        User user = new User(request, password);
        userRepository.save(user);

        return new UserSignUpResponse(user);
    }

    @Transactional
    public UserSignInResponse signin(UserSignInRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        // 회원가입된 유저인지 확인
        User user = userRepository.findByUsernameOrThrow(username);

        // 비밀 번호가 맞는지 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CareerthonException(USER_PW_ERROR);
        }

        String token = jwtUtil.createToken(user.getId(), user.getUsername(), user.getRole());
        return new UserSignInResponse(token);
    }

//    // JWT 테스트용
//    public String check() {
//        return "all good";
//    }
}

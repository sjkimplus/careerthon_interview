package com.sparta.careerthon_interview.user.service;

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
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserSignUpResponse signup(UserSignUpRequest request) {

        // 빈 값이 없는지 확인
        if (request.getUsername().isEmpty() || request.getPassword().isEmpty() || request.getNickname()==null) {
            throw new HotSixException(MISSING_FORMAT);
        }

        // 중복 유저 확인
        Optional<User> username = userRepository.findByUsername(request.getUsername());
        if (checkEmail.isPresent()) throw new HotSixException(USER_ID_DUPLICATION);

////         validate email format
//        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
//        if (!requestDto.getEmail().matches(emailPattern)) {
//            throw new HotSixException(WRONG_FORMAT); // Throw exception for invalid email format
//        }

//        // Validate password format
//        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
//        if (!requestDto.getPassword().matches(passwordPattern)) {
//            throw new HotSixException(WRONG_FORMAT); // Throw exception for invalid password format
//        }


        // 유저 생성
        String password = passwordEncoder.encode(request.getPassword());
        User user = new User(request, password);
        userRepository.save(user);

        return new UserSignUpResponse(username);
    }

    @Transactional
    public UserSignInResponse signin(UserSignInRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        // 회원가입된 유저인지 확인
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new HotSixException(USER_NOT_FOUND));

        // 비밀 번호가 맞는지 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new HotSixException(USER_PW_ERROR);
        }

        String token = jwtUtil.createToken(user.getId(), user.getEmail(), user.getRole());
        return new SignInResponseDto(user, token);
    }

}

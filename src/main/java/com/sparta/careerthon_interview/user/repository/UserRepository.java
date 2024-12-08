package com.sparta.careerthon_interview.user.repository;

import com.sparta.careerthon_interview.common.exception.CareerthonException;
import com.sparta.careerthon_interview.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.sparta.careerthon_interview.common.exception.ErrorCode.USER_NOT_FOUND;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    // Custom method that throws an exception if the user is not found
    default User findByUsernameOrThrow(String username) {
        return findByUsername(username).orElseThrow(() ->
                new CareerthonException(USER_NOT_FOUND));
    }
}
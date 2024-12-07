package com.sparta.careerthon_interview.user.repository;

import com.sparta.careerthon_interview.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
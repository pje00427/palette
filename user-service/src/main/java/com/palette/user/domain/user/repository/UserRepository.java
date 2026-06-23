package com.palette.user.domain.user.repository;

import com.palette.user.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일로 회원 조회 (로그인, 중복 확인)
    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    // 이메일 중복 확인
    boolean existsByEmail(String email);
}
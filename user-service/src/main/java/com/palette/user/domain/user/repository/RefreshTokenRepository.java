package com.palette.user.domain.user.repository;

import com.palette.user.domain.user.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // 토큰 값으로 조회 (재발급, 로그아웃 시)
    Optional<RefreshToken> findByToken(String token);

    // 유저의 기존 토큰 전체 폐기 (재로그인 시)
    void deleteByUserId(Long userId);
}
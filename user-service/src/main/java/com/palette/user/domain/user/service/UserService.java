package com.palette.user.domain.user.service;

import com.palette.common.exception.DomainException;
import com.palette.user.domain.user.dto.request.LoginRequest;
import com.palette.user.domain.user.dto.request.PasswordUpdateRequest;
import com.palette.user.domain.user.dto.request.SignupRequest;
import com.palette.user.domain.user.dto.request.UserUpdateRequest;
import com.palette.user.domain.user.dto.response.LoginResponse;
import com.palette.user.domain.user.dto.response.TokenResponse;
import com.palette.user.domain.user.dto.response.UserResponse;
import com.palette.user.domain.user.entity.RefreshToken;
import com.palette.user.domain.user.entity.User;
import com.palette.user.domain.user.repository.RefreshTokenRepository;
import com.palette.user.domain.user.repository.UserRepository;
import com.palette.user.exception.UserExceptionCode;
import com.palette.user.security.JwtUtil;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    // 회원가입
    @Transactional
    public void signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DomainException(UserExceptionCode.DUPLICATE_EMAIL);
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();
        userRepository.save(user);
    }

    // 로그인 — 액세스 토큰 + 리프레시 토큰 발급
    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndDeletedAtIsNull(request.getEmail())
                .orElseThrow(() -> new DomainException(UserExceptionCode.NOT_FOUND_USER));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new DomainException(UserExceptionCode.INVALID_PASSWORD);
        }

        String accessToken = jwtUtil.generateAccessToken(
                user.getId(), user.getEmail(), user.getRole().name());

        // 기존 토큰 삭제 후 새로 발급 (재로그인 시 토큰 중복 방지)
        refreshTokenRepository.deleteByUserId(user.getId());
        String rawRefreshToken = jwtUtil.generateRefreshToken(user.getId());
        refreshTokenRepository.save(RefreshToken.builder()
                .userId(user.getId())
                .token(rawRefreshToken)
                .expiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000))
                .build());

        return LoginResponse.of(accessToken, rawRefreshToken);
    }

    // 토큰 재발급
    @Transactional
    public TokenResponse reissue(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new DomainException(UserExceptionCode.INVALID_REFRESH_TOKEN));

        if (token.isExpired()) {
            throw new DomainException(UserExceptionCode.EXPIRED_REFRESH_TOKEN);
        }
        if (token.isRevoked()) {
            throw new DomainException(UserExceptionCode.INVALID_REFRESH_TOKEN);
        }

        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new DomainException(UserExceptionCode.NOT_FOUND_USER));

        String newAccessToken = jwtUtil.generateAccessToken(
                user.getId(), user.getEmail(), user.getRole().name());

        return TokenResponse.of(newAccessToken);
    }

    // 로그아웃 — 리프레시 토큰 폐기
    @Transactional
    public void logout(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken)
                .ifPresent(RefreshToken::revoke);
    }

    // 내 정보 조회
    @Transactional(readOnly = true)
    public UserResponse getMe(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(UserExceptionCode.NOT_FOUND_USER));
        return UserResponse.from(user);
    }

    // 내 정보 수정
    @Transactional
    public void updateMe(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(UserExceptionCode.NOT_FOUND_USER));
        user.updateInfo(request.getName(), request.getPhone(), request.getAddress());
    }

    // 비밀번호 변경
    @Transactional
    public void updatePassword(Long userId, PasswordUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(UserExceptionCode.NOT_FOUND_USER));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new DomainException(UserExceptionCode.INVALID_PASSWORD);
        }
        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
    }

    // 회원 탈퇴
    @Transactional
    public void deleteMe(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(UserExceptionCode.NOT_FOUND_USER));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new DomainException(UserExceptionCode.INVALID_PASSWORD);
        }
        user.delete();
    }
}
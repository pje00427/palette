package com.palette.user.domain.user.controller;

import com.palette.common.response.ApiResponse;
import com.palette.user.domain.user.dto.request.LoginRequest;
import com.palette.user.domain.user.dto.request.PasswordUpdateRequest;
import com.palette.user.domain.user.dto.request.SignupRequest;
import com.palette.user.domain.user.dto.request.UserUpdateRequest;
import com.palette.user.domain.user.dto.response.LoginResponse;
import com.palette.user.domain.user.dto.response.TokenResponse;
import com.palette.user.domain.user.dto.response.UserResponse;
import com.palette.user.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@Valid @RequestBody SignupRequest request) {
        userService.signup(request);
        return ApiResponse.ok();
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.ok(userService.login(request));
    }

    // 토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponse>> reissue(@RequestHeader("Refresh-Token") String refreshToken) {
        return ApiResponse.ok(userService.reissue(refreshToken));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Refresh-Token") String refreshToken) {
        userService.logout(refreshToken);
        return ApiResponse.ok();
    }

    // 내 정보 조회 — JWT에서 추출한 userId 사용
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMe(@AuthenticationPrincipal Long userId) {
        return ApiResponse.ok(userService.getMe(userId));
    }

    // 내 정보 수정
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<Void>> updateMe(@AuthenticationPrincipal Long userId,
                                                      @RequestBody UserUpdateRequest request) {
        userService.updateMe(userId, request);
        return ApiResponse.ok();
    }

    // 비밀번호 변경
    @PatchMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(@AuthenticationPrincipal Long userId,
                                                            @Valid @RequestBody PasswordUpdateRequest request) {
        userService.updatePassword(userId, request);
        return ApiResponse.ok();
    }

    // 회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteMe(@AuthenticationPrincipal Long userId,
                                                      @RequestParam String password) {
        userService.deleteMe(userId, password);
        return ApiResponse.ok();
    }
}
package com.palette.product.domain.drop.controller;

import com.palette.common.response.ApiResponse;
import com.palette.product.domain.drop.dto.response.DropNotificationResponse;
import com.palette.product.domain.drop.service.DropNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Drop Notification", description = "드롭 사전 알림 API")
public class DropNotificationController {

    private final DropNotificationService dropNotificationService;

    // 드롭 알림 신청
    // 사용자가 특정 드롭의 오픈 알림을 받겠다고 신청
    @Operation(summary = "드롭 알림 신청")
    @PostMapping("/drops/{dropId}/notifications")
    public ResponseEntity<ApiResponse<DropNotificationResponse>> subscribeDropNotification(
            @PathVariable Long dropId,
            @AuthenticationPrincipal UserDetails userDetails) {  // JWT 토큰에서 userId 자동 추출

        // UserDetails의 username이 userId (user-service에서 설정함)
        Long userId = Long.parseLong(userDetails.getUsername());

        DropNotificationResponse response = dropNotificationService
                .createNotification(dropId, userId);

        return ApiResponse.ok(response);
    }

    // 내가 신청한 드롭 알림 목록 조회
    // 로그인한 사용자가 신청한 모든 알림을 조회
    @Operation(summary = "내 드롭 알림 목록 조회")
    @GetMapping("/users/notifications")
    public ResponseEntity<ApiResponse<List<DropNotificationResponse>>> getMyDropNotifications(
            @AuthenticationPrincipal UserDetails userDetails) {  // JWT 토큰에서 userId 자동 추출

        // UserDetails의 username이 userId (user-service에서 설정함)
        Long userId = Long.parseLong(userDetails.getUsername());

        List<DropNotificationResponse> response = dropNotificationService
                .getMyNotifications(userId);

        return ApiResponse.ok(response);
    }
}
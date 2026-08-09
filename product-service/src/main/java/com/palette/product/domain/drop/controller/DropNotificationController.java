package com.palette.product.domain.drop.controller;

import com.palette.common.response.ApiResponse;
import com.palette.product.domain.drop.dto.response.DropNotificationResponse;
import com.palette.product.domain.drop.service.DropNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Drop Notification", description = "드롭 사전 알림 API")
public class DropNotificationController {

    private final DropNotificationService dropNotificationService;

    @Operation(summary = "드롭 알림 신청")
    @PostMapping("/drops/{dropId}/notifications")
    public ResponseEntity<ApiResponse<DropNotificationResponse>> subscribeDropNotification(
        @PathVariable Long dropId,
        @RequestHeader("X-User-Id") Long userId) {

        DropNotificationResponse response = dropNotificationService
            .createNotification(dropId, userId);

        return ApiResponse.ok(response);
    }

    @Operation(summary = "내 드롭 알림 목록 조회")
    @GetMapping("/users/notifications")
    public ResponseEntity<ApiResponse<List<DropNotificationResponse>>> getMyDropNotifications(
        @RequestHeader("X-User-Id") Long userId) {

        List<DropNotificationResponse> response = dropNotificationService
            .getMyNotifications(userId);

        return ApiResponse.ok(response);
    }
}
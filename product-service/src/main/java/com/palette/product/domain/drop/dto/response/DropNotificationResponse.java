package com.palette.product.domain.drop.dto.response;

import com.palette.product.domain.drop.entity.DropNotification;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DropNotificationResponse {

    private Long id;
    private Long dropId;
    private Long userId;
    private Boolean isNotified;
    private LocalDateTime createdAt;

    public static DropNotificationResponse from(DropNotification dropNotification) {
        return DropNotificationResponse.builder()
                .id(dropNotification.getId())
                .dropId(dropNotification.getDrop().getId())
                .userId(dropNotification.getUserId())
                .isNotified(dropNotification.getIsNotified())
                .createdAt(dropNotification.getCreatedAt())
                .build();
    }
}
package com.palette.product.domain.drop.dto.response;

import com.palette.product.domain.drop.entity.Drop;
import com.palette.product.domain.drop.entity.DropStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DropResponse {

    private Long id;
    private Long productId;
    private String productName;
    private LocalDateTime scheduledAt;
    private LocalDateTime endAt;
    private Integer totalQuantity;
    private DropStatus status;
    private LocalDateTime createdAt;

    public static DropResponse from(Drop drop) {
        return DropResponse.builder()
                .id(drop.getId())
                .productId(drop.getProduct().getId())
                .productName(drop.getProduct().getName())
                .scheduledAt(drop.getScheduledAt())
                .endAt(drop.getEndAt())
                .totalQuantity(drop.getTotalQuantity())
                .status(drop.getStatus())
                .createdAt(drop.getCreatedAt())
                .build();
    }
}
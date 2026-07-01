package com.palette.product.domain.drop.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DropCreateRequest {

    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;

    @NotNull(message = "오픈 날짜/시간은 필수입니다.")
    private LocalDateTime scheduledAt;

    @NotNull(message = "마감 날짜/시간은 필수입니다.")
    private LocalDateTime endAt;

    @NotNull(message = "한정 수량은 필수입니다.")
    @Positive(message = "수량은 0보다 커야 합니다.")
    private Integer totalQuantity;
}
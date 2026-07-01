package com.palette.product.domain.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductOptionCreateRequest {

    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;

    @NotBlank(message = "옵션명은 필수입니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    @Positive(message = "가격은 0보다 커야 합니다.")
    private BigDecimal price;
}
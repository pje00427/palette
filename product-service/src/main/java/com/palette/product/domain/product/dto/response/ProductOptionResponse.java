package com.palette.product.domain.product.dto.response;

import com.palette.product.domain.product.entity.ProductOption;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductOptionResponse {

    private Long id;
    private Long productId;
    private String name;
    private BigDecimal price;
    private Boolean isActive;

    public static ProductOptionResponse from(ProductOption productOption) {
        return ProductOptionResponse.builder()
                .id(productOption.getId())
                .productId(productOption.getProduct().getId())
                .name(productOption.getName())
                .price(productOption.getPrice())
                .isActive(productOption.getIsActive())
                .build();
    }
}
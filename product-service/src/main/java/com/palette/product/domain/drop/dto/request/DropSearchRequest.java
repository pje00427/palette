package com.palette.product.domain.drop.dto.request;

import com.palette.product.domain.drop.entity.DropStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DropSearchRequest {

    private DropStatus status;   // null이면 전체 상태 조회
    private Long productId;      // null이면 전체 상품 조회
}
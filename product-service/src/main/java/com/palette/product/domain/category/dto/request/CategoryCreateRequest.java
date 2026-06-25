package com.palette.product.domain.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryCreateRequest {

    @NotBlank(message = "카테고리 이름은 필수입니다.")
    private String name;

    @NotNull(message = "정렬 순서는 필수입니다.")
    private Integer sortOrder;
}
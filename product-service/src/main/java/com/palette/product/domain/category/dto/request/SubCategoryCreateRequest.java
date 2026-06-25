package com.palette.product.domain.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubCategoryCreateRequest {

    @NotNull(message = "카테고리 ID는 필수입니다.")
    private Long categoryId;

    @NotBlank(message = "서브카테고리 이름은 필수입니다.")
    private String name;

    @NotNull(message = "정렬 순서는 필수입니다.")
    private Integer sortOrder;
}
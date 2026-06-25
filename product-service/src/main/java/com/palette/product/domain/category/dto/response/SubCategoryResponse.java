package com.palette.product.domain.category.dto.response;

import com.palette.product.domain.category.entity.SubCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubCategoryResponse {

    private Long id;
    private Long categoryId;
    private String name;
    private Integer sortOrder;
    private Boolean isActive;

    public static SubCategoryResponse from(SubCategory subCategory) {
        return SubCategoryResponse.builder()
                .id(subCategory.getId())
                .categoryId(subCategory.getCategory().getId())
                .name(subCategory.getName())
                .sortOrder(subCategory.getSortOrder())
                .isActive(subCategory.getIsActive())
                .build();
    }
}
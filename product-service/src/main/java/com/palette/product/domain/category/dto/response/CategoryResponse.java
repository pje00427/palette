package com.palette.product.domain.category.dto.response;

import com.palette.product.domain.category.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {

    private Long id;
    private String name;
    private Integer sortOrder;
    private Boolean isActive;

    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .sortOrder(category.getSortOrder())
                .isActive(category.getIsActive())
                .build();
    }
}
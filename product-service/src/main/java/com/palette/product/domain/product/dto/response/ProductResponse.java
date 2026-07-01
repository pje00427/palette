package com.palette.product.domain.product.dto.response;

import com.palette.product.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

    private Long id;
    private Long artistId;
    private String artistName;
    private Long subCategoryId;
    private String subCategoryName;
    private String name;
    private String description;
    private String thumbnailImage;
    private Boolean isActive;

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .artistId(product.getArtist().getId())
                .artistName(product.getArtist().getName())
                .subCategoryId(product.getSubCategory().getId())
                .subCategoryName(product.getSubCategory().getName())
                .name(product.getName())
                .description(product.getDescription())
                .thumbnailImage(product.getThumbnailImage())
                .isActive(product.getIsActive())
                .build();
    }
}
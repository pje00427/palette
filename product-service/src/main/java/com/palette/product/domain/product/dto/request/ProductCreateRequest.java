package com.palette.product.domain.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotNull(message = "아티스트 ID는 필수입니다.")
    private Long artistId;

    @NotNull(message = "서브카테고리 ID는 필수입니다.")
    private Long subCategoryId;

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    private String description;

    private String thumbnailImage;
}
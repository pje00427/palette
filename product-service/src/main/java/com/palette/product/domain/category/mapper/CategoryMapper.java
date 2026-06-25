package com.palette.product.domain.category.mapper;

import com.palette.product.domain.category.dto.response.CategoryResponse;
import com.palette.product.domain.category.dto.response.SubCategoryResponse;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    List<CategoryResponse> findAllActive();

    Optional<CategoryResponse> findById(Long id);

    List<SubCategoryResponse> findSubCategoriesByCategoryId(Long categoryId);
}
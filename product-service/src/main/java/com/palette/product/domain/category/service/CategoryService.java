package com.palette.product.domain.category.service;

import com.palette.common.exception.DomainException;
import com.palette.product.domain.category.dto.request.CategoryCreateRequest;
import com.palette.product.domain.category.dto.request.SubCategoryCreateRequest;
import com.palette.product.domain.category.dto.response.CategoryResponse;
import com.palette.product.domain.category.dto.response.SubCategoryResponse;
import com.palette.product.domain.category.entity.Category;
import com.palette.product.domain.category.entity.SubCategory;
import com.palette.product.domain.category.mapper.CategoryMapper;
import com.palette.product.domain.category.repository.CategoryRepository;
import com.palette.product.domain.category.repository.SubCategoryRepository;
import com.palette.product.exception.ProductExceptionCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryMapper categoryMapper;

    // 카테고리 등록 (JPA 쓰기)
    @Transactional
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .sortOrder(request.getSortOrder())
                .build();
        Category saved = categoryRepository.save(category);
        return CategoryResponse.from(saved);
    }

    // 카테고리 전체 조회 (MyBatis 읽기)
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategories() {
        return categoryMapper.findAllActive();
    }

    // 카테고리 단건 조회 (MyBatis 읽기)
    @Transactional(readOnly = true)
    public CategoryResponse getCategory(Long id) {
        return categoryMapper.findById(id)
                .orElseThrow(() -> new DomainException(ProductExceptionCode.NOT_FOUND_CATEGORY));
    }

    // 서브카테고리 등록 (JPA 쓰기)
    @Transactional
    public SubCategoryResponse createSubCategory(SubCategoryCreateRequest request) {
        Category category = categoryRepository.findByIdAndIsActiveTrue(request.getCategoryId())
                .orElseThrow(() -> new DomainException(ProductExceptionCode.NOT_FOUND_CATEGORY));
        SubCategory subCategory = SubCategory.builder()
                .category(category)
                .name(request.getName())
                .sortOrder(request.getSortOrder())
                .build();
        SubCategory saved = subCategoryRepository.save(subCategory);
        return SubCategoryResponse.from(saved);
    }

    // 특정 카테고리의 서브카테고리 목록 조회 (MyBatis 읽기)
    @Transactional(readOnly = true)
    public List<SubCategoryResponse> getSubCategories(Long categoryId) {
        return categoryMapper.findSubCategoriesByCategoryId(categoryId);
    }
}
package com.palette.product.domain.category.controller;

import com.palette.common.response.ApiResponse;
import com.palette.product.domain.category.dto.request.CategoryCreateRequest;
import com.palette.product.domain.category.dto.request.SubCategoryCreateRequest;
import com.palette.product.domain.category.dto.response.CategoryResponse;
import com.palette.product.domain.category.dto.response.SubCategoryResponse;
import com.palette.product.domain.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Category", description = "카테고리 API")
public class CategoryController {

    private final CategoryService categoryService;

    // 어드민 — 카테고리 등록
    @Operation(summary = "[어드민] 카테고리 등록")
    @PostMapping("/admin/categories")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CategoryCreateRequest request) {
        return ApiResponse.ok(categoryService.createCategory(request));
    }

    // 어드민 — 서브카테고리 등록
    @Operation(summary = "[어드민] 서브카테고리 등록")
    @PostMapping("/admin/categories/sub")
    public ResponseEntity<ApiResponse<SubCategoryResponse>> createSubCategory(
            @Valid @RequestBody SubCategoryCreateRequest request) {
        return ApiResponse.ok(categoryService.createSubCategory(request));
    }

    // 카테고리 전체 조회
    @Operation(summary = "카테고리 목록 조회")
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategories() {
        return ApiResponse.ok(categoryService.getCategories());
    }

    // 카테고리 단건 조회
    @Operation(summary = "카테고리 단건 조회")
    @GetMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategory(@PathVariable Long id) {
        return ApiResponse.ok(categoryService.getCategory(id));
    }

    // 특정 카테고리의 서브카테고리 목록 조회
    @Operation(summary = "서브카테고리 목록 조회")
    @GetMapping("/categories/{id}/sub")
    public ResponseEntity<ApiResponse<List<SubCategoryResponse>>> getSubCategories(
            @PathVariable Long id) {
        return ApiResponse.ok(categoryService.getSubCategories(id));
    }
}
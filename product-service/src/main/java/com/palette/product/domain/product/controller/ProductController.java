package com.palette.product.domain.product.controller;

import com.palette.common.response.ApiResponse;
import com.palette.product.domain.product.dto.request.ProductCreateRequest;
import com.palette.product.domain.product.dto.request.ProductOptionCreateRequest;
import com.palette.product.domain.product.dto.response.ProductOptionResponse;
import com.palette.product.domain.product.dto.response.ProductResponse;
import com.palette.product.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product", description = "상품 API")
public class ProductController {

    private final ProductService productService;

    // 어드민 — 상품 등록
    @Operation(summary = "[어드민] 상품 등록")
    @PostMapping("/admin/products")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody ProductCreateRequest request) {
        return ApiResponse.ok(productService.createProduct(request));
    }

    // 어드민 — 상품 옵션 등록
    @Operation(summary = "[어드민] 상품 옵션 등록")
    @PostMapping("/admin/products/options")
    public ResponseEntity<ApiResponse<ProductOptionResponse>> createProductOption(
            @Valid @RequestBody ProductOptionCreateRequest request) {
        return ApiResponse.ok(productService.createProductOption(request));
    }

    // 상품 전체 조회
    @Operation(summary = "상품 목록 조회")
    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts() {
        return ApiResponse.ok(productService.getProducts());
    }

    // 상품 단건 조회
    @Operation(summary = "상품 단건 조회")
    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long id) {
        return ApiResponse.ok(productService.getProduct(id));
    }

    // 특정 상품의 옵션 목록 조회
    @Operation(summary = "상품 옵션 목록 조회")
    @GetMapping("/products/{id}/options")
    public ResponseEntity<ApiResponse<List<ProductOptionResponse>>> getProductOptions(
            @PathVariable Long id) {
        return ApiResponse.ok(productService.getProductOptions(id));
    }
}
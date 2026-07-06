package com.palette.product.domain.drop.controller;

import com.palette.common.response.ApiResponse;
import com.palette.product.domain.drop.dto.request.DropCreateRequest;
import com.palette.product.domain.drop.dto.request.DropSearchRequest;
import com.palette.product.domain.drop.dto.response.DropResponse;
import com.palette.product.domain.drop.service.DropService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Drop", description = "드롭 API")
public class DropController {

    private final DropService dropService;

    // 어드민 — 드롭 등록
    @Operation(summary = "[어드민] 드롭 등록")
    @PostMapping("/admin/drops")
    public ResponseEntity<ApiResponse<DropResponse>> createDrop(
            @Valid @RequestBody DropCreateRequest request) {
        return ApiResponse.ok(dropService.createDrop(request));
    }

    // 어드민 — 드롭 취소
    @Operation(summary = "[어드민] 드롭 취소")
    @DeleteMapping("/admin/drops/{id}")
    public ResponseEntity<ApiResponse<Void>> cancelDrop(@PathVariable Long id) {
        dropService.cancelDrop(id);
        return ApiResponse.ok();
    }

    // 드롭 목록 조회
    @Operation(summary = "드롭 목록 조회")
    @GetMapping("/drops")
    public ResponseEntity<ApiResponse<List<DropResponse>>> getDrops() {
        return ApiResponse.ok(dropService.getDrops());
    }

    // 드롭 단건 조회
    @Operation(summary = "드롭 단건 조회")
    @GetMapping("/drops/{id}")
    public ResponseEntity<ApiResponse<DropResponse>> getDrop(@PathVariable Long id) {
        return ApiResponse.ok(dropService.getDrop(id));
    }
    // 드롭 동적 검색
    @Operation(summary = "드롭 검색 (status / productId 필터)")
    @GetMapping("/drops/search")
    public ResponseEntity<ApiResponse<List<DropResponse>>> searchDrops(
            DropSearchRequest request) {
        return ApiResponse.ok(dropService.searchDrops(request));
    }
}
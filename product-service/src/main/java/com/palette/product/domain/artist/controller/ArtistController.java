package com.palette.product.domain.artist.controller;

import com.palette.common.response.ApiResponse;
import com.palette.product.domain.artist.dto.request.ArtistCreateRequest;
import com.palette.product.domain.artist.dto.response.ArtistResponse;
import com.palette.product.domain.artist.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Artist", description = "아티스트 API")
public class ArtistController {

    private final ArtistService artistService;

    // 어드민 — 아티스트 등록
    @Operation(summary = "[어드민] 아티스트 등록")
    @PostMapping("/admin/artists")
    public ResponseEntity<ApiResponse<ArtistResponse>> createArtist(
            @Valid @RequestBody ArtistCreateRequest request) {
        return ApiResponse.ok(artistService.createArtist(request));
    }

    // 어드민 — 아티스트 소프트 딜리트
    @Operation(summary = "[어드민] 아티스트 삭제")
    @DeleteMapping("/admin/artists/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ApiResponse.ok();
    }

    // 아티스트 전체 조회
    @Operation(summary = "아티스트 목록 조회")
    @GetMapping("/artists")
    public ResponseEntity<ApiResponse<List<ArtistResponse>>> getArtists() {
        return ApiResponse.ok(artistService.getArtists());
    }

    // 아티스트 단건 조회
    @Operation(summary = "아티스트 단건 조회")
    @GetMapping("/artists/{id}")
    public ResponseEntity<ApiResponse<ArtistResponse>> getArtist(@PathVariable Long id) {
        return ApiResponse.ok(artistService.getArtist(id));
    }
}
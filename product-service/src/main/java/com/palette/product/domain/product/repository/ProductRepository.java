package com.palette.product.domain.product.repository;

import com.palette.product.domain.product.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 소프트 딜리트 — 삭제 안 된 상품 단건 조회
    Optional<Product> findByIdAndDeletedAtIsNull(Long id);

    // 특정 아티스트의 활성 상품 존재 여부 (아티스트 삭제 시 확인용)
    boolean existsByArtistIdAndDeletedAtIsNull(Long artistId);

    // 특정 소분류의 활성 상품 존재 여부 (소분류 삭제 시 확인용)
    boolean existsBySubCategoryIdAndDeletedAtIsNull(Long subCategoryId);
}
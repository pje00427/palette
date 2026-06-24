package com.palette.product.domain.product.repository;

import com.palette.product.domain.product.entity.ProductOption;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    // 특정 상품의 활성화된 옵션 목록 조회
    List<ProductOption> findAllByProductIdAndDeletedAtIsNull(Long productId);

    // 소프트 딜리트 — 삭제 안 된 옵션 단건 조회
    Optional<ProductOption> findByIdAndDeletedAtIsNull(Long id);

    // 특정 상품의 옵션 존재 여부 (상품 삭제 시 확인용)
    boolean existsByProductIdAndDeletedAtIsNull(Long productId);
}
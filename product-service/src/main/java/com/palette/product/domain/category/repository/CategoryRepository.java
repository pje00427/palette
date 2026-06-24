package com.palette.product.domain.category.repository;

import com.palette.product.domain.category.entity.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // 이름으로 카테고리 조회 (중복 확인용)
    boolean existsByName(String name);

    // 활성화된 카테고리만 정렬 순서대로 조회
    List<Category> findAllByIsActiveTrueOrderBySortOrderAsc();

    // 활성화된 카테고리 단건 조회
    Optional<Category> findByIdAndIsActiveTrue(Long id);
}
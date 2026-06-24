package com.palette.product.domain.category.repository;

import com.palette.product.domain.category.entity.SubCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    // 같은 카테고리 내 이름 중복 확인
    boolean existsByCategoryIdAndName(Long categoryId, String name);

    // 특정 카테고리의 활성화된 소분류 정렬 순서대로 조회
    List<SubCategory> findAllByCategoryIdAndIsActiveTrueOrderBySortOrderAsc(Long categoryId);

    // 활성화된 소분류 단건 조회
    Optional<SubCategory> findByIdAndIsActiveTrue(Long id);
}
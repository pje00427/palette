package com.palette.product.domain.category.entity;

import com.palette.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Table(name = "categories")
@Entity
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // UNIQUE 제약 — 같은 이름의 카테고리 중복 방지
    @Column(nullable = false, unique = true)
    String name;

    // 목록 정렬 순서 (낮을수록 먼저 노출)
    @Column(nullable = false)
    Integer sortOrder;

    @Column(nullable = false)
    Boolean isActive;

    @Builder
    public Category(String name, Integer sortOrder) {
        this.name = name;
        this.sortOrder = sortOrder;
        this.isActive = true;  // 생성 시 기본값 활성
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }
}
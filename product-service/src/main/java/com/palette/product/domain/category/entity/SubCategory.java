package com.palette.product.domain.category.entity;

import com.palette.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Table(name = "sub_categories",
        uniqueConstraints = @UniqueConstraint(columnNames = {"category_id", "name"}))
@Entity
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 같은 DB 내부라 @ManyToOne으로 직접 참조 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    Category category;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    Integer sortOrder;

    @Column(nullable = false)
    Boolean isActive;

    @Builder
    public SubCategory(Category category, String name, Integer sortOrder) {
        this.category = category;
        this.name = name;
        this.sortOrder = sortOrder;
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }
}
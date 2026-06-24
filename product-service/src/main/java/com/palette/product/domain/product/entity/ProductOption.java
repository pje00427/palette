package com.palette.product.domain.product.entity;

import com.palette.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "product_options")
@Entity
@Getter
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 같은 DB 내부라 @ManyToOne으로 직접 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    // 예: 누드핑크 3.5g
    @Column(nullable = false, length = 100)
    String name;

    // DECIMAL(10,2) — 소수점 2자리까지 (정확한 금액 계산)
    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal price;

    @Column(nullable = false)
    Boolean isActive;

    LocalDateTime deletedAt;

    @Builder
    public ProductOption(Product product, String name, BigDecimal price) {
        this.product = product;
        this.name = name;
        this.price = price;
        this.isActive = true;
    }

    public void update(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
        this.isActive = false;
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}
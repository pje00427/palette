package com.palette.product.domain.drop.entity;

import com.palette.common.entity.BaseEntity;
import com.palette.product.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDateTime;

@Table(name = "drops")
@Entity
@Getter
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Drop extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    // 드롭 오픈 날짜/시간
    @Column(nullable = false)
    LocalDateTime scheduledAt;

    // 드롭 마감 날짜/시간 (null이면 재고 소진까지)
    LocalDateTime endAt;

    @Column(nullable = false)
    Integer totalQuantity;

    // SCHEDULED / OPEN / SOLD_OUT / CLOSED / CANCELLED
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    DropStatus status;

    LocalDateTime deletedAt;

    @Builder
    public Drop(Product product, LocalDateTime scheduledAt,
                LocalDateTime endAt, Integer totalQuantity) {
        this.product = product;
        this.scheduledAt = scheduledAt;
        this.endAt = endAt;
        this.totalQuantity = totalQuantity;
        this.status = DropStatus.SCHEDULED;  // 생성 시 기본값
    }

    public void open() {
        this.status = DropStatus.OPEN;
    }

    public void soldOut() {
        this.status = DropStatus.SOLD_OUT;
    }

    public void close() {
        this.status = DropStatus.CLOSED;
    }

    public void cancel() {
        this.status = DropStatus.CANCELLED;
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isOpen() {
        return this.status == DropStatus.OPEN;
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}
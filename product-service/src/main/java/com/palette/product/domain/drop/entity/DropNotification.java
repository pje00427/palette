package com.palette.product.domain.drop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Table(name = "drop_notifications")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DropNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 같은 DB라 @ManyToOne 참조 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drop_id", nullable = false)
    Drop drop;

    // user-service DB가 달라서 ID만 보관 (물리적 FK 없음)
    @Column(nullable = false)
    Long userId;

    // 알림 발송 여부 (추후 구현 예정)
    @Column(nullable = false)
    Boolean isNotified;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @Builder
    public DropNotification(Drop drop, Long userId) {
        this.drop = drop;
        this.userId = userId;
        this.isNotified = false;
    }
}
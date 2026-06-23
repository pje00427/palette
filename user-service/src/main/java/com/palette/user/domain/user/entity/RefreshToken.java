package com.palette.user.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Table(name = "refresh_tokens")
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long userId;

    @Column(nullable = false, length = 512)
    String token;

    @Column(nullable = false)
    LocalDateTime expiresAt;

    LocalDateTime revokedAt;

    // BaseEntity 상속 안 하므로 createdAt만 직접 선언
    @CreatedDate
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @Builder
    public RefreshToken(Long userId, String token, LocalDateTime expiresAt) {
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    // 로그아웃 시 토큰 폐기
    public void revoke() {
        this.revokedAt = LocalDateTime.now();
    }

    public boolean isRevoked() {
        return this.revokedAt != null;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }

    // 폐기 안됐고 만료 안됐으면 유효
    public boolean isValid() {
        return !isRevoked() && !isExpired();
    }
}
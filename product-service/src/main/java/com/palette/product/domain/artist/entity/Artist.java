package com.palette.product.domain.artist.entity;

import com.palette.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDateTime;

@Table(name = "artists")
@Entity
@Getter
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Artist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    // TEXT 타입 — 긴 소개글
    @Column(columnDefinition = "TEXT")
    String bio;

    // S3 이미지 URL
    @Column(length = 500)
    String profileImage;

    @Column(length = 300)
    String instagramUrl;

    @Column(nullable = false)
    Boolean isActive;

    LocalDateTime deletedAt;

    @Builder
    public Artist(String name, String bio, String profileImage, String instagramUrl) {
        this.name = name;
        this.bio = bio;
        this.profileImage = profileImage;
        this.instagramUrl = instagramUrl;
        this.isActive = true;
    }

    public void update(String name, String bio, String profileImage, String instagramUrl) {
        this.name = name;
        this.bio = bio;
        this.profileImage = profileImage;
        this.instagramUrl = instagramUrl;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
        this.isActive = false;
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}
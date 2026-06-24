package com.palette.product.domain.product.entity;

import com.palette.common.entity.BaseEntity;
import com.palette.product.domain.artist.entity.Artist;
import com.palette.product.domain.category.entity.SubCategory;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDateTime;

@Table(name = "products")
@Entity
@Getter
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 같은 DB 내부라 @ManyToOne으로 직접 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id", nullable = false)
    SubCategory subCategory;

    @Column(nullable = false, length = 200)
    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    // S3 이미지 URL
    @Column(length = 500)
    String thumbnailImage;

    @Column(nullable = false)
    Boolean isActive;

    LocalDateTime deletedAt;

    @Builder
    public Product(Artist artist, SubCategory subCategory, String name,
                   String description, String thumbnailImage) {
        this.artist = artist;
        this.subCategory = subCategory;
        this.name = name;
        this.description = description;
        this.thumbnailImage = thumbnailImage;
        this.isActive = true;
    }

    public void update(String name, String description, String thumbnailImage) {
        this.name = name;
        this.description = description;
        this.thumbnailImage = thumbnailImage;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
        this.isActive = false;
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}
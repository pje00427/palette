package com.palette.product.domain.artist.dto.response;

import com.palette.product.domain.artist.entity.Artist;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArtistResponse {

    private Long id;
    private String name;
    private String bio;
    private String profileImage;
    private String instagramUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public static ArtistResponse from(Artist artist) {
        return ArtistResponse.builder()
                .id(artist.getId())
                .name(artist.getName())
                .bio(artist.getBio())
                .profileImage(artist.getProfileImage())
                .instagramUrl(artist.getInstagramUrl())
                .isActive(artist.getIsActive())
                .createdAt(artist.getCreatedAt())
                .build();
    }
}
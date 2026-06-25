package com.palette.product.domain.artist.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArtistCreateRequest {

    @NotBlank(message = "아티스트 이름은 필수입니다.")
    private String name;

    private String bio;

    private String profileImage;

    private String instagramUrl;
}
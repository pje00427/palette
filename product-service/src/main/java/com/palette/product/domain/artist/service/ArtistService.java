package com.palette.product.domain.artist.service;

import com.palette.common.exception.DomainException;
import com.palette.product.domain.artist.dto.request.ArtistCreateRequest;
import com.palette.product.domain.artist.dto.response.ArtistResponse;
import com.palette.product.domain.artist.entity.Artist;
import com.palette.product.domain.artist.mapper.ArtistMapper;
import com.palette.product.domain.artist.repository.ArtistRepository;
import com.palette.product.exception.ProductExceptionCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    // 아티스트 등록 (JPA 쓰기)
    @Transactional
    public ArtistResponse createArtist(ArtistCreateRequest request) {
        Artist artist = Artist.builder()
                .name(request.getName())
                .bio(request.getBio())
                .profileImage(request.getProfileImage())
                .instagramUrl(request.getInstagramUrl())
                .build();
        Artist saved = artistRepository.save(artist);
        return ArtistResponse.from(saved);
    }

    // 아티스트 전체 조회 (MyBatis 읽기)
    @Transactional(readOnly = true)
    public List<ArtistResponse> getArtists() {
        return artistMapper.findAllActive();
    }

    // 아티스트 단건 조회 (MyBatis 읽기)
    @Transactional(readOnly = true)
    public ArtistResponse getArtist(Long id) {
        return artistMapper.findById(id)
                .orElseThrow(() -> new DomainException(ProductExceptionCode.NOT_FOUND_ARTIST));
    }

    // 아티스트 소프트 딜리트 (JPA 쓰기)
    @Transactional
    public void deleteArtist(Long id) {
        Artist artist = artistRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new DomainException(ProductExceptionCode.NOT_FOUND_ARTIST));
        artist.delete();
    }
}
package com.palette.product.domain.artist.repository;

import com.palette.product.domain.artist.entity.Artist;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    // 소프트 딜리트 — 삭제 안 된 아티스트만 단건 조회
    Optional<Artist> findByIdAndDeletedAtIsNull(Long id);

    // 활성화된 아티스트 목록 조회
    List<Artist> findAllByIsActiveTrueAndDeletedAtIsNull();

    // 이름 중복 확인
    boolean existsByNameAndDeletedAtIsNull(String name);
}
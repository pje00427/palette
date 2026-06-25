package com.palette.product.domain.artist.mapper;

import com.palette.product.domain.artist.dto.response.ArtistResponse;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArtistMapper {

    List<ArtistResponse> findAllActive();

    Optional<ArtistResponse> findById(Long id);
}
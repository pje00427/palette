package com.palette.product.domain.drop.mapper;

import com.palette.product.domain.drop.dto.request.DropSearchRequest;
import com.palette.product.domain.drop.dto.response.DropResponse;
import com.palette.product.domain.drop.entity.DropStatus;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DropMapper {

    List<DropResponse> findAll();

    Optional<DropResponse> findById(Long id);

    List<DropResponse> findByStatus(DropStatus status);

    List<DropResponse> search(DropSearchRequest request);
}
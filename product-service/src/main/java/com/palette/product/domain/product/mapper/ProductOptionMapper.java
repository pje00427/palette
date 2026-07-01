package com.palette.product.domain.product.mapper;

import com.palette.product.domain.product.dto.response.ProductOptionResponse;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductOptionMapper {

    Optional<ProductOptionResponse> findById(Long id);
}
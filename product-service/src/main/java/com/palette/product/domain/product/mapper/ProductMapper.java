package com.palette.product.domain.product.mapper;

import com.palette.product.domain.product.dto.response.ProductOptionResponse;
import com.palette.product.domain.product.dto.response.ProductResponse;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

    List<ProductResponse> findAllActive();

    Optional<ProductResponse> findById(Long id);

    List<ProductOptionResponse> findOptionsByProductId(Long productId);
}
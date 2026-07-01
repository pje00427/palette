// product-service/src/main/java/com/palette/product/domain/product/service/ProductService.java

package com.palette.product.domain.product.service;

import com.palette.common.exception.DomainException;
import com.palette.product.domain.artist.entity.Artist;
import com.palette.product.domain.artist.repository.ArtistRepository;
import com.palette.product.domain.category.entity.SubCategory;
import com.palette.product.domain.category.repository.SubCategoryRepository;
import com.palette.product.domain.product.dto.request.ProductCreateRequest;
import com.palette.product.domain.product.dto.request.ProductOptionCreateRequest;
import com.palette.product.domain.product.dto.response.ProductOptionResponse;
import com.palette.product.domain.product.dto.response.ProductResponse;
import com.palette.product.domain.product.entity.Product;
import com.palette.product.domain.product.entity.ProductOption;
import com.palette.product.domain.product.mapper.ProductMapper;
import com.palette.product.domain.product.mapper.ProductOptionMapper;
import com.palette.product.domain.product.repository.ProductOptionRepository;
import com.palette.product.domain.product.repository.ProductRepository;
import com.palette.product.exception.ProductExceptionCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ArtistRepository artistRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductMapper productMapper;
    private final ProductOptionMapper productOptionMapper;

    // 상품 등록 (JPA 쓰기)
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        Artist artist = artistRepository.findByIdAndDeletedAtIsNull(request.getArtistId())
                .orElseThrow(() -> new DomainException(ProductExceptionCode.NOT_FOUND_ARTIST));

        SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                .orElseThrow(() -> new DomainException(ProductExceptionCode.NOT_FOUND_SUB_CATEGORY));

        Product product = Product.builder()
                .artist(artist)
                .subCategory(subCategory)
                .name(request.getName())
                .description(request.getDescription())
                .thumbnailImage(request.getThumbnailImage())
                .build();

        Product saved = productRepository.save(product);
        return ProductResponse.from(saved);
    }

    // 상품 전체 조회 (MyBatis 읽기)
    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts() {
        return productMapper.findAllActive();
    }

    // 상품 단건 조회 (MyBatis 읽기)
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        return productMapper.findById(id)
                .orElseThrow(() -> new DomainException(ProductExceptionCode.NOT_FOUND_PRODUCT));
    }

    // 상품 옵션 등록 (JPA 쓰기)
    @Transactional
    public ProductOptionResponse createProductOption(ProductOptionCreateRequest request) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(request.getProductId())
                .orElseThrow(() -> new DomainException(ProductExceptionCode.NOT_FOUND_PRODUCT));

        ProductOption productOption = ProductOption.builder()
                .product(product)
                .name(request.getName())
                .price(request.getPrice())
                .build();

        ProductOption saved = productOptionRepository.save(productOption);
        return ProductOptionResponse.from(saved);
    }

    // 특정 상품의 옵션 목록 조회 (MyBatis 읽기)
    @Transactional(readOnly = true)
    public List<ProductOptionResponse> getProductOptions(Long productId) {
        return productMapper.findOptionsByProductId(productId);
    }
}
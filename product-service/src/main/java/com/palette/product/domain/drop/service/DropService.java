package com.palette.product.domain.drop.service;

import com.palette.common.exception.DomainException;
import com.palette.product.domain.drop.dto.request.DropCreateRequest;
import com.palette.product.domain.drop.dto.request.DropSearchRequest;
import com.palette.product.domain.drop.dto.response.DropResponse;
import com.palette.product.domain.drop.entity.Drop;
import com.palette.product.domain.drop.mapper.DropMapper;
import com.palette.product.domain.drop.repository.DropRepository;
import com.palette.product.domain.product.entity.Product;
import com.palette.product.domain.product.repository.ProductRepository;
import com.palette.product.exception.ProductExceptionCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DropService {

    private final DropRepository dropRepository;
    private final ProductRepository productRepository;
    private final DropMapper dropMapper;

    // 드롭 생성 (어드민) — JPA 쓰기
    @CacheEvict(value = "drops", allEntries = true)
    @Transactional
    public DropResponse createDrop(DropCreateRequest request) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(request.getProductId())
                .orElseThrow(() -> new DomainException(ProductExceptionCode.NOT_FOUND_PRODUCT));

        Drop drop = Drop.builder()
                .product(product)
                .scheduledAt(request.getScheduledAt())
                .endAt(request.getEndAt())
                .totalQuantity(request.getTotalQuantity())
                .build();

        dropRepository.save(drop);
        return DropResponse.from(drop);
    }

    // 드롭 목록 조회 — MyBatis 읽기
    @Cacheable(value = "drops", key = "'all'")
    @Transactional(readOnly = true)
    public List<DropResponse> getDrops() {
        return dropMapper.findAll();
    }

    // 드롭 단건 조회 — MyBatis 읽기
    @Cacheable(value = "drops", key = "#dropId")
    @Transactional(readOnly = true)
    public DropResponse getDrop(Long dropId) {
        return dropMapper.findById(dropId)
                .orElseThrow(() -> new DomainException(ProductExceptionCode.NOT_FOUND_DROP));
    }

    // 드롭 취소 (어드민) — JPA 쓰기
    @CacheEvict(value = "drops", allEntries = true)
    @Transactional
    public void cancelDrop(Long dropId) {
        Drop drop = dropRepository.findByIdAndDeletedAtIsNull(dropId)
                .orElseThrow(() -> new DomainException(ProductExceptionCode.NOT_FOUND_DROP));
        drop.cancel();
    }
    // 드롭 동적 검색 — MyBatis 읽기
    @Cacheable(value = "drops", key = "#request.status + '_' + #request.productId")
    @Transactional(readOnly = true)
    public List<DropResponse> searchDrops(DropSearchRequest request) {
        return dropMapper.search(request);
    }
}
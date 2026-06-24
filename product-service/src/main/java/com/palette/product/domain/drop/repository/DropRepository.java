package com.palette.product.domain.drop.repository;

import com.palette.product.domain.drop.entity.Drop;
import com.palette.product.domain.drop.entity.DropStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DropRepository extends JpaRepository<Drop, Long> {

    // 소프트 딜리트 — 삭제 안 된 드롭 단건 조회
    Optional<Drop> findByIdAndDeletedAtIsNull(Long id);

    // 특정 상품의 활성 드롭 조회
    Optional<Drop> findByProductIdAndDeletedAtIsNull(Long productId);

    // @Scheduled용 — 오픈 시간이 지난 SCHEDULED 상태 드롭 조회
    List<Drop> findAllByStatusAndScheduledAtLessThanEqual(
            DropStatus status, LocalDateTime now);

    // @Scheduled용 — 마감 시간이 지난 OPEN 상태 드롭 조회
    List<Drop> findAllByStatusAndEndAtLessThanEqual(
            DropStatus status, LocalDateTime now);
}
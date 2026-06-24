package com.palette.product.domain.drop.repository;

import com.palette.product.domain.drop.entity.DropNotification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DropNotificationRepository extends JpaRepository<DropNotification, Long> {

    // 중복 신청 확인 (같은 드롭에 같은 유저)
    boolean existsByDropIdAndUserId(Long dropId, Long userId);

    // 드롭 오픈 시 알림 미발송 대상자 조회 (추후 알림 발송용)
    List<DropNotification> findAllByDropIdAndIsNotifiedFalse(Long dropId);
}
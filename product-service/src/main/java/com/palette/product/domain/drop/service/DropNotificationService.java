package com.palette.product.domain.drop.service;

import com.palette.common.exception.DomainException;
import com.palette.product.domain.drop.dto.response.DropNotificationResponse;
import com.palette.product.domain.drop.entity.Drop;
import com.palette.product.domain.drop.entity.DropNotification;
import com.palette.product.domain.drop.repository.DropNotificationRepository;
import com.palette.product.domain.drop.repository.DropRepository;
import com.palette.product.exception.ProductExceptionCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DropNotificationService {

    private final DropNotificationRepository dropNotificationRepository;
    private final DropRepository dropRepository;

    // 드롭 알림 신청
    // userId: JWT 토큰에서 추출한 현재 로그인한 사용자 ID
    // dropId: URL 파라미터로 받은 드롭 ID
    @Transactional
    public DropNotificationResponse createNotification(Long dropId, Long userId) {

        // 1️⃣ 드롭이 존재하는지 확인 (deleted_at IS NULL)
        Drop drop = dropRepository.findByIdAndDeletedAtIsNull(dropId)
                .orElseThrow(() -> new DomainException(ProductExceptionCode.NOT_FOUND_DROP));

        // 2️⃣ 이미 같은 드롭에 알림 신청했는지 확인 (중복 방지)
        boolean alreadyExists = dropNotificationRepository
                .existsByDropIdAndUserId(dropId, userId);

        if (alreadyExists) {
            throw new DomainException(ProductExceptionCode.ALREADY_NOTIFIED);
        }

        // 3️⃣ 새로운 알림 신청 생성
        DropNotification notification = DropNotification.builder()
                .drop(drop)
                .userId(userId)
                .isNotified(false)  // 초기값: 아직 알림 안 보냄
                .build();

        // 4️⃣ DB에 저장
        dropNotificationRepository.save(notification);

        return DropNotificationResponse.from(notification);
    }

    // 내가 신청한 알림 목록 조회
    // userId: JWT 토큰에서 추출한 현재 로그인한 사용자 ID
    @Transactional(readOnly = true)
    public List<DropNotificationResponse> getMyNotifications(Long userId) {

        // 1️⃣ user_id로 모든 알림 조회
        List<DropNotification> notifications = dropNotificationRepository
                .findByUserIdOrderByCreatedAtDesc(userId);

        // 2️⃣ DTO로 변환해서 반환
        return notifications.stream()
                .map(DropNotificationResponse::from)
                .toList();
    }
}
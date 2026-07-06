package com.palette.product.domain.drop.scheduler;

import com.palette.product.domain.drop.entity.Drop;
import com.palette.product.domain.drop.entity.DropStatus;
import com.palette.product.domain.drop.repository.DropRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DropScheduler {

    private final DropRepository dropRepository;

    // 1분마다 — SCHEDULED → OPEN
    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void openScheduledDrops() {
        List<Drop> drops = dropRepository
                .findAllByStatusAndScheduledAtLessThanEqual(
                        DropStatus.SCHEDULED, LocalDateTime.now());

        for (Drop drop : drops) {
            drop.open();
            log.info("[DropScheduler] OPEN — dropId: {}, scheduledAt: {}",
                    drop.getId(), drop.getScheduledAt());
        }
    }

    // 1분마다 — OPEN → CLOSED (endAt 지난 것만)
    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void closeExpiredDrops() {
        List<Drop> drops = dropRepository
                .findAllByStatusAndEndAtLessThanEqual(
                        DropStatus.OPEN, LocalDateTime.now());

        for (Drop drop : drops) {
            drop.close();
            log.info("[DropScheduler] CLOSED — dropId: {}, endAt: {}",
                    drop.getId(), drop.getEndAt());
        }
    }
}
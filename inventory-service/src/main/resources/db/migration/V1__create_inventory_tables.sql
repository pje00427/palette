-- =============================================
-- inventory-service DB : palette_inventory
-- =============================================

-- 재고 테이블
CREATE TABLE inventories
(
    id                BIGINT   NOT NULL AUTO_INCREMENT,
    product_option_id BIGINT   NOT NULL UNIQUE,    -- product-service ID만 보관 (다른 DB, FK 없음)
    quantity          INT      NOT NULL DEFAULT 0, -- 현재 재고 수량
    version           BIGINT   NOT NULL DEFAULT 0, -- 낙관적 락 (@Version) — 2차 동시성 방어
    created_at        DATETIME NOT NULL,
    updated_at        DATETIME NOT NULL,
    PRIMARY KEY (id)
);

-- 재고 변동 이력 테이블
CREATE TABLE inventory_logs
(
    id             BIGINT   NOT NULL AUTO_INCREMENT,
    inventory_id   BIGINT   NOT NULL,              -- inventories.id 참조 (@ManyToOne)
    order_id       BIGINT,                         -- 주문 추적용 (어떤 주문으로 변동됐는지)
    type           VARCHAR(20) NOT NULL,           -- DEDUCT(차감) / RESTORE(복구)
    quantity_delta INT      NOT NULL,              -- 변동 수량 (차감: -N, 복구: +N)
    reason         VARCHAR(200),                   -- 변동 사유
    created_at     DATETIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_inventory_logs_inventory FOREIGN KEY (inventory_id) REFERENCES inventories (id)
);

-- 인덱스
CREATE INDEX idx_inventories_product_option_id ON inventories (product_option_id);
CREATE INDEX idx_inventory_logs_inventory_id ON inventory_logs (inventory_id);
CREATE INDEX idx_inventory_logs_order_id ON inventory_logs (order_id);

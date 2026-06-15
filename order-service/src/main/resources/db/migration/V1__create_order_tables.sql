-- =============================================
-- order-service DB : palette_order
-- =============================================

-- 주문 테이블
CREATE TABLE orders
(
    id               BIGINT        NOT NULL AUTO_INCREMENT,
    user_id          BIGINT        NOT NULL,       -- user-service ID만 보관 (다른 DB, FK 없음)
    delivery_address VARCHAR(255)  NOT NULL,       -- 주문 시 입력한 배송지
    total_amount     DECIMAL(10,2) NOT NULL,       -- 주문 총 금액
    status           VARCHAR(20)   NOT NULL,       -- 주문 상태 Enum
    -- ORDER_PLACED → PAYMENT_COMPLETED
    -- → SHIPPING_PENDING → IN_TRANSIT
    -- → DELIVERED → CANCELLED
    created_at       DATETIME      NOT NULL,
    updated_at       DATETIME      NOT NULL,
    PRIMARY KEY (id)
);

-- 주문 상품 테이블 (DDD 스냅샷 — 주문 시점 상품명/옵션명/가격 복사)
CREATE TABLE order_items
(
    id                BIGINT        NOT NULL AUTO_INCREMENT,
    order_id          BIGINT        NOT NULL,      -- orders.id 참조 (@ManyToOne)
    product_option_id BIGINT        NOT NULL,      -- product-service ID만 보관 (다른 DB, FK 없음)
    product_name      VARCHAR(200)  NOT NULL,      -- 주문 시점 상품명 스냅샷
    option_name       VARCHAR(100)  NOT NULL,      -- 주문 시점 옵션명 스냅샷
    price             DECIMAL(10,2) NOT NULL,      -- 주문 시점 가격 스냅샷
    quantity          INT           NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id)
);

-- 결제 테이블
CREATE TABLE payments
(
    id                BIGINT        NOT NULL AUTO_INCREMENT,
    order_id          BIGINT        NOT NULL,      -- orders.id 참조 (@ManyToOne)
    toss_payment_key  VARCHAR(200)  NOT NULL,      -- 토스페이먼츠 PID (결제 묶음 키)
    amount            DECIMAL(10,2) NOT NULL,      -- 결제 금액
    refunded_amount   DECIMAL(10,2) NOT NULL DEFAULT 0, -- 환불 누계 금액
    payment_method    VARCHAR(50),                 -- 결제 수단 (카드, 가상계좌 등)
    status            VARCHAR(20)   NOT NULL,      -- COMPLETED / CANCELLED / REFUNDED
    created_at        DATETIME      NOT NULL,
    updated_at        DATETIME      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_payments_order FOREIGN KEY (order_id) REFERENCES orders (id)
);

-- 결제 액션 이력 테이블 (PG 거래 단위 이력 — TID)
-- payments(PID) 1개에 payment_action_logs(TID) N개
-- 결제 1건, 부분환불 여러 건 모두 별도 행으로 기록
CREATE TABLE payment_action_logs
(
    id          BIGINT        NOT NULL AUTO_INCREMENT,
    payment_id  BIGINT        NOT NULL,            -- payments.id 참조 (@ManyToOne)
    tid         VARCHAR(200)  NOT NULL,            -- 토스페이먼츠 거래 ID (액션마다 새로 발급)
    type        VARCHAR(20)   NOT NULL,            -- PAYMENT / CANCEL / PARTIAL_REFUND
    amount      DECIMAL(10,2) NOT NULL,            -- 이번 액션 금액
    reason      VARCHAR(200),                      -- 취소/환불 사유
    created_at  DATETIME      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_payment_action_logs_payment FOREIGN KEY (payment_id) REFERENCES payments (id)
);

-- 인덱스
CREATE INDEX idx_orders_user_id ON orders (user_id);
CREATE INDEX idx_orders_status ON orders (status);
CREATE INDEX idx_order_items_order_id ON order_items (order_id);
CREATE INDEX idx_order_items_product_option_id ON order_items (product_option_id);
CREATE INDEX idx_payments_order_id ON payments (order_id);
CREATE INDEX idx_payments_toss_payment_key ON payments (toss_payment_key);
CREATE INDEX idx_payment_action_logs_payment_id ON payment_action_logs (payment_id);

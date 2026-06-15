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
    toss_payment_key  VARCHAR(200)  NOT NULL,      -- 토스페이먼츠 결제 키
    amount            DECIMAL(10,2) NOT NULL,      -- 결제 금액
    refunded_amount   DECIMAL(10,2) NOT NULL DEFAULT 0, -- 환불 금액 (부분 환불 대비)
    payment_method    VARCHAR(50),                 -- 결제 수단 (카드, 가상계좌 등)
    status            VARCHAR(20)   NOT NULL,      -- COMPLETED / CANCELLED / REFUNDED
    created_at        DATETIME      NOT NULL,
    updated_at        DATETIME      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_payments_order FOREIGN KEY (order_id) REFERENCES orders (id)
);

-- 인덱스
CREATE INDEX idx_orders_user_id ON orders (user_id);
CREATE INDEX idx_orders_status ON orders (status);
CREATE INDEX idx_order_items_order_id ON order_items (order_id);
CREATE INDEX idx_order_items_product_option_id ON order_items (product_option_id);
CREATE INDEX idx_payments_order_id ON payments (order_id);
CREATE INDEX idx_payments_toss_payment_key ON payments (toss_payment_key);

-- =============================================
-- product-service DB : palette_product
-- =============================================

-- 아티스트 테이블
CREATE TABLE artists
(
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(100) NOT NULL,
    bio           TEXT,
    profile_image VARCHAR(500),                    -- S3 이미지 URL
    instagram_url VARCHAR(300),                    -- SNS 링크 (1개만)
    is_active     TINYINT(1)   NOT NULL DEFAULT 1,
    created_at    DATETIME     NOT NULL,
    updated_at    DATETIME     NOT NULL,
    deleted_at    DATETIME,                        -- 소프트 딜리트
    PRIMARY KEY (id)
);

-- 카테고리 테이블 (대분류) — 하드 딜리트
CREATE TABLE categories
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    name       VARCHAR(50) NOT NULL UNIQUE,        -- 예: 립, 스킨케어, 아이
    sort_order INT         NOT NULL DEFAULT 0,
    is_active  TINYINT(1)  NOT NULL DEFAULT 1,
    created_at DATETIME    NOT NULL,
    updated_at DATETIME    NOT NULL,
    PRIMARY KEY (id)
);

-- 서브카테고리 테이블 (소분류) — 하드 딜리트
CREATE TABLE sub_categories
(
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    category_id BIGINT      NOT NULL,              -- categories.id 참조 (@ManyToOne)
    name        VARCHAR(50) NOT NULL,
    sort_order  INT         NOT NULL DEFAULT 0,
    is_active   TINYINT(1)  NOT NULL DEFAULT 1,
    created_at  DATETIME    NOT NULL,
    updated_at  DATETIME    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_sub_categories_category FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT uq_sub_category UNIQUE (category_id, name) -- 같은 카테고리 내 중복 방지
);

-- 상품 테이블
CREATE TABLE products
(
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    artist_id        BIGINT       NOT NULL,        -- artists.id 참조 (@ManyToOne)
    sub_category_id  BIGINT       NOT NULL,        -- sub_categories.id 참조 (@ManyToOne)
    name             VARCHAR(200) NOT NULL,
    description      TEXT,
    thumbnail_image  VARCHAR(500),                 -- S3 이미지 URL
    is_active        TINYINT(1)   NOT NULL DEFAULT 1,
    created_at       DATETIME     NOT NULL,
    updated_at       DATETIME     NOT NULL,
    deleted_at       DATETIME,                     -- 소프트 딜리트
    PRIMARY KEY (id),
    CONSTRAINT fk_products_artist FOREIGN KEY (artist_id) REFERENCES artists (id),
    CONSTRAINT fk_products_sub_category FOREIGN KEY (sub_category_id) REFERENCES sub_categories (id)
);

-- 상품 옵션 테이블
CREATE TABLE product_options
(
    id         BIGINT        NOT NULL AUTO_INCREMENT,
    product_id BIGINT        NOT NULL,             -- products.id 참조 (@ManyToOne)
    name       VARCHAR(100)  NOT NULL,             -- 예: 누드핑크 3.5g
    price      DECIMAL(10,2) NOT NULL,
    is_active  TINYINT(1)    NOT NULL DEFAULT 1,
    created_at DATETIME      NOT NULL,
    updated_at DATETIME      NOT NULL,
    deleted_at DATETIME,                           -- 소프트 딜리트
    PRIMARY KEY (id),
    CONSTRAINT fk_product_options_product FOREIGN KEY (product_id) REFERENCES products (id)
);

-- 드롭 스케줄 테이블
CREATE TABLE drops
(
    id             BIGINT   NOT NULL AUTO_INCREMENT,
    product_id     BIGINT   NOT NULL,              -- products.id 참조 (@ManyToOne)
    scheduled_at   DATETIME NOT NULL,              -- 오픈 날짜/시간
    end_at         DATETIME,                       -- 마감 날짜/시간
    total_quantity INT      NOT NULL,              -- 한정 수량
    status         VARCHAR(20) NOT NULL,           -- SCHEDULED/OPEN/SOLD_OUT/CLOSED/CANCELLED
    created_at     DATETIME NOT NULL,
    updated_at     DATETIME NOT NULL,
    deleted_at     DATETIME,                       -- 소프트 딜리트 (취소 이력 보존)
    PRIMARY KEY (id),
    CONSTRAINT fk_drops_product FOREIGN KEY (product_id) REFERENCES products (id)
);

-- 사전 알림 신청 테이블
CREATE TABLE drop_notifications
(
    id           BIGINT     NOT NULL AUTO_INCREMENT,
    drop_id      BIGINT     NOT NULL,              -- drops.id 참조 (@ManyToOne)
    user_id      BIGINT     NOT NULL,              -- user-service ID만 보관 (다른 DB, FK 없음)
    is_notified  TINYINT(1) NOT NULL DEFAULT 0,    -- 알림 발송 여부 (추후 구현)
    created_at   DATETIME   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_drop_notifications_drop FOREIGN KEY (drop_id) REFERENCES drops (id)
);

-- 인덱스
CREATE INDEX idx_artists_deleted_at ON artists (deleted_at);
CREATE INDEX idx_products_artist_id ON products (artist_id);
CREATE INDEX idx_products_sub_category_id ON products (sub_category_id);
CREATE INDEX idx_products_deleted_at ON products (deleted_at);
CREATE INDEX idx_product_options_product_id ON product_options (product_id);
CREATE INDEX idx_drops_product_id ON drops (product_id);
CREATE INDEX idx_drops_scheduled_at ON drops (scheduled_at);
CREATE INDEX idx_drops_status ON drops (status);
CREATE INDEX idx_drop_notifications_drop_id ON drop_notifications (drop_id);
CREATE INDEX idx_drop_notifications_user_id ON drop_notifications (user_id);

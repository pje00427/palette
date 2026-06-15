-- =============================================
-- user-service DB : palette_user
-- =============================================

-- 회원 테이블
CREATE TABLE users
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,             -- BCrypt 해시
    name       VARCHAR(50)  NOT NULL,
    phone      VARCHAR(20),
    address    VARCHAR(255),                       -- 기본 배송지 (단일)
    role       VARCHAR(20)  NOT NULL DEFAULT 'USER', -- USER / ADMIN
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL,
    deleted_at DATETIME,                           -- 소프트 딜리트
    PRIMARY KEY (id)
);

-- JWT 리프레시 토큰 테이블 (DB 저장 방식 — Redis 아님)
CREATE TABLE refresh_tokens
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    user_id    BIGINT       NOT NULL,              -- users.id 참조 (앱 레벨, FK 없음)
    token      VARCHAR(512) NOT NULL,
    expires_at DATETIME     NOT NULL,
    created_at DATETIME     NOT NULL,
    revoked_at DATETIME,                           -- 로그아웃/폐기 시간
    PRIMARY KEY (id)
);

-- 인덱스
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_deleted_at ON users (deleted_at);
CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens (user_id);
CREATE INDEX idx_refresh_tokens_token ON refresh_tokens (token);

-- todo_app 데이터베이스를 먼저 만들고 사용하세요.
-- CREATE DATABASE todo_app CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- USE todo_app;

DROP TABLE IF EXISTS todo;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS member;

CREATE TABLE member (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    name       VARCHAR(50)  NOT NULL,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- member_id가 NULL이면 모든 회원이 함께 쓰는 공용 카테고리
CREATE TABLE category (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT       NULL,
    name       VARCHAR(50)  NOT NULL,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_category_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE todo (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id   BIGINT       NOT NULL,
    category_id BIGINT       NULL,
    title       VARCHAR(100) NOT NULL,
    content     TEXT         NULL,
    due_date    DATE         NULL,
    completed   BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_todo_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE,
    CONSTRAINT fk_todo_category FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE SET NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 공용 기본 카테고리 (member_id = NULL)
INSERT INTO category (member_id, name) VALUES
    (NULL, '업무'),
    (NULL, '개인'),
    (NULL, '공부');

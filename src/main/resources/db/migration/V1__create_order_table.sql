-- V1__create_order_table.sql
CREATE TABLE `orders` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,     -- 訂單的唯一 ID
    user_id BIGINT NOT NULL,                  -- 關聯到 user 表的外鍵
    product_id BIGINT NOT NULL,               -- 關聯到 product 表的外鍵
    quantity INT NOT NULL,                    -- 記錄訂購數量
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 訂單創建時間
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 訂單更新時間
    deleted_at TIMESTAMP NULL DEFAULT NULL,
    -- 外鍵約束
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (product_id) REFERENCES product(id),

    -- 防止同一使用者對同一產品重複下單
    UNIQUE (user_id, product_id)
);

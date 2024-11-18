-- V1__create_payment_table.sql
CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,     -- 支付的唯一 ID
    order_id BIGINT NOT NULL UNIQUE,          -- 關聯到 order 表的外鍵，每筆訂單僅有一個支付記錄
    total_amount INT NOT NULL,                -- 總金額（以最低單位存儲，例如分）
    is_paid BOOLEAN DEFAULT FALSE,            -- 支付狀態，FALSE 表示未支付，TRUE 表示已支付

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 支付記錄創建時間
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 支付記錄更新時間
    deleted_at TIMESTAMP NULL DEFAULT NULL,
    -- 外鍵約束
    FOREIGN KEY (order_id) REFERENCES `order`(id)
);

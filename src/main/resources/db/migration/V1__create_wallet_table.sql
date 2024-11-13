-- V1__create_wallet_table.sql
CREATE TABLE wallets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,     -- 錢包的唯一 ID
    user_id BIGINT NOT NULL UNIQUE,           -- 關聯到 user 表的外鍵，且每個用戶僅有一個錢包
    balance INT DEFAULT 0 NOT NULL,           -- 錢包餘額，以整數形式存儲最低單位（例如分）

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 錢包建立時間
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 錢包更新時間

    -- 外鍵約束
    FOREIGN KEY (user_id) REFERENCES user(id)
);

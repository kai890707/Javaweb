package com.example.javaweb.dto.response;

import com.example.javaweb.entity.Order;

import java.time.LocalDateTime;

public class OrderResponse {

    private Long id;               // Order ID
    private Long productId;        // 關聯的 Product ID
    private Long userId;           // 關聯的 User ID
    private Integer quantity;      // 訂購數量
    private LocalDateTime createdAt; // 訂單創建時間

    // 構造函數，從 Order 實體轉換
    public OrderResponse(Order order) {
        this.id = order.getId();
        this.productId = order.getProduct().getId();
        this.userId = order.getUser().getId();
        this.quantity = order.getQuantity();
        this.createdAt = order.getCreatedAt();
    }

    // Getters 和 Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

package com.example.javaweb.dto.request;

public class OrderRequest {

    private Long productId; // 用於映射 product 的 ID
    private Long userId;    // 用於映射 user 的 ID
    private Integer quantity; // 訂單的數量

    // Getters 和 Setters
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
}

package com.example.javaweb.dto.response;

import com.example.javaweb.entity.Order;
import com.example.javaweb.entity.Payment;

public class PaymentResponse {

    private Long id;             // Payment ID
    private OrderResponse order;        // 關聯的 Order ID
    private Integer totalAmount; // 總金額
    private Boolean isPaid;      // 是否已支付

    // 構造函數，將實體轉換為 DTO
    public PaymentResponse(Payment payment) {
        this.id = payment.getId();
        this.order = new OrderResponse(payment.getOrder());
        this.totalAmount = payment.getTotalAmount();
        this.isPaid = payment.getIsPaid();
    }

    // Getters 和 Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderResponse getOrder() {
        return order;
    }

    public void setOrder(OrderResponse order) {
        this.order = order;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}

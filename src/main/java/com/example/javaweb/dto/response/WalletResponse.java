package com.example.javaweb.dto.response;

import com.example.javaweb.entity.Wallet;

public class WalletResponse {
    private Long id;
    private Long userId;
    private Integer balance;

    public WalletResponse(Wallet wallet) {
        this.id = wallet.getId();
        this.userId = wallet.getUser().getId();
        this.balance = wallet.getBalance();
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getBalance() {
        return balance;
    }
    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}

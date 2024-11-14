package com.example.javaweb.dto;

import com.example.javaweb.entity.User;
import com.example.javaweb.entity.Wallet;

public class UserWalletResponse {
    private User user;
    private Wallet wallet;

    public UserWalletResponse(User user, Wallet wallet) {
        this.user = user;
        this.wallet = wallet;
    }
    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}

package com.example.javaweb.dto.response;

import com.example.javaweb.entity.User;

public class UserResponse {
    private Long id;
    private String account;

    public UserResponse(User user) {
        this.id = user.getId();
        this.account = user.getAccount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}

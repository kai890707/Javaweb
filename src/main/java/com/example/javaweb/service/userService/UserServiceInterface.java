package com.example.javaweb.service.userService;

import com.example.javaweb.entity.User;

import java.util.List;

public interface UserServiceInterface {

    List<User> getAllUser();

    List<User> getUsersWithDeleted();

    List<User> getDeletedUsers();

    User getUserById(Long id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);
}

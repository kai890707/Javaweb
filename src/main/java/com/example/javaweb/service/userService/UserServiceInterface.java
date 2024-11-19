package com.example.javaweb.service.userService;

import com.example.javaweb.entity.User;

import java.util.List;

public interface UserServiceInterface {

    List<User> getAllUser();

    List<User> getUsersWithDeleted();

    List<User> getDeletedUsers();

    User getUserById(Long id);

    User createUser(User user);

    User updateUser(Long id, User user);

    Boolean deleteUser(Long id);
}

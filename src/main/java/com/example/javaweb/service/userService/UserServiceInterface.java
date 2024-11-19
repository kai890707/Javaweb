package com.example.javaweb.service.userService;

import com.example.javaweb.dto.request.UserRequest;
import com.example.javaweb.dto.response.UserResponse;
import com.example.javaweb.entity.User;

import java.util.List;

public interface UserServiceInterface {

    List<UserResponse> getAllUser();

    List<UserResponse> getUsersWithDeleted();

    List<UserResponse> getDeletedUsers();

    UserResponse getUserById(Long id);

    UserResponse createUser(UserRequest user);

    UserResponse updateUser(Long id, UserRequest user);

    Boolean deleteUser(Long id);
}

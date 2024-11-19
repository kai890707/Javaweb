package com.example.javaweb.service.userService;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.dto.request.UserRequest;
import com.example.javaweb.dto.response.PaymentResponse;
import com.example.javaweb.entity.User;
import com.example.javaweb.repository.UserRepository;
import com.example.javaweb.dto.response.UserResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    /**
     * 取得使用者，不含軟刪除資料
     * @return 使用者
     */
    @Override
    public List<UserResponse> getAllUser() {
        List<User> users = this.userRepository.findAll();
        return users.stream()
                .map(UserResponse::new)
                .toList();
    }

    /**
     * 查詢所有記錄（包括已刪除）
     * @return 使用者
     */
    public List<UserResponse> getUsersWithDeleted() {
        List<User> users = this.userRepository.findAllIncludingDeleted();
        return users.stream()
                .map(UserResponse::new)
                .toList();
    }

    /**
     * 查詢已刪除的記錄
     * @return 使用者
     */
    public List<UserResponse> getDeletedUsers() {
        List<User> users = this.userRepository.findDeleted();
        return users.stream()
                .map(UserResponse::new)
                .toList();
    }

    /**
     * 取得使用者 with user id
     * @param id 使用者ID
     * @return 使用者實體
     * @throws ResourceNotFoundException
     */
    @Override
    public UserResponse getUserById(Long id) throws ResourceNotFoundException {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        return new UserResponse(user);
    }

    /**
     * 取得UserID實體
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    public User getUserEntityById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + id));
    }

    /**
     * 新增使用者
     * @param UserRequest 使用者資料
     * @return 新增後的使用者實體
     */
    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRequest.getAccount() == null || userRequest.getPassword() == null) {
            throw new IllegalArgumentException("User account and password must not be null");
        }
        User user = new User();
        user.setAccount(userRequest.getAccount());
        user.setPassword(userRequest.getPassword());
        User userCreated = this.userRepository.save(user);
        return new UserResponse(userCreated);
    }

    /**
     * 更新使用者
     * @param user 使用者資料
     * @return 更新後的使用者實體
     * @throws ResourceNotFoundException
     */
    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) throws ResourceNotFoundException {
        if (userRequest.getPassword() == null) {
            throw new IllegalArgumentException("User password must not be null");
        }
        User userEntity = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id : " + id));

        userEntity.setPassword(userRequest.getPassword());
        userEntity = this.userRepository.save(userEntity);
        return new UserResponse(userEntity);
    }

    /**
     * 刪除使用者
     * @param id 使用者ID
     * @throws ResourceNotFoundException
     * @return 是否軟刪除成功
     */
    @Override
    public Boolean deleteUser(Long id) throws ResourceNotFoundException {
        User userEntity = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id : " + id));
        userEntity.setDeletedAt(LocalDateTime.now());
        LocalDateTime deleteTime = this.userRepository.save(userEntity).getDeletedAt();
        return deleteTime != null;
    }
}

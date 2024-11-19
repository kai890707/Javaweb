package com.example.javaweb.service.userService;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.entity.Product;
import com.example.javaweb.entity.User;
import com.example.javaweb.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    /**
     * 查詢所有記錄（包括已刪除）
     * @return 使用者
     */
    public List<User> getUsersWithDeleted() {
        return this.userRepository.findAllIncludingDeleted();
    }

    /**
     * 查詢已刪除的記錄
     * @return 使用者
     */
    public List<User> getDeletedUsers() {
        return this.userRepository.findDeleted();
    }

    /**
     * 取得使用者 with user id
     * @param id 使用者ID
     * @return 使用者實體
     * @throws ResourceNotFoundException
     */
    @Override
    public User getUserById(Long id) throws ResourceNotFoundException {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    /**
     * 新增使用者
     * @param user 使用者資料
     * @return 新增後的使用者實體
     */
    @Override
    public User createUser(User user) {
        return this.userRepository.save(user);
    }

    /**
     * 更新使用者
     * @param user 使用者資料
     * @return 更新後的使用者實體
     * @throws ResourceNotFoundException
     */
    @Override
    public User updateUser(Long id, User user) throws ResourceNotFoundException {
        User userEntity = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id : " + id));
        userEntity.setPassword(user.getPassword());
        return this.userRepository.save(userEntity);
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

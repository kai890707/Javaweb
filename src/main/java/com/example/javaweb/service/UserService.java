package com.example.javaweb.service;

import com.example.javaweb.dto.UserWalletResponse;
import com.example.javaweb.entity.Product;
import com.example.javaweb.entity.User;
import com.example.javaweb.entity.Wallet;
import com.example.javaweb.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final WalletService walletService;

    @Autowired
    public UserService(UserRepository userRepository, WalletService walletService) {
        this.userRepository = userRepository;
        this.walletService = walletService;
    }

    /**
     * 撈出所有User
     * @return
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * 透過使用者ID尋找使用者
     * @param id 使用者ID
     * @return 使用者資料
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 新增與更新使用者
     * @param user 使用者 Entity
     * @return 儲存後的使用者實體
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * 刪除使用者
     * @param id
     */
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * 合併User與Wallet結果
     * @param user
     * @return
     */
    @Transactional
    public UserWalletResponse createUserWithWallet(User user) {
        // 保存 User
        User createdUser = userRepository.save(user);

        // 為 User 創建 Wallet，初始化餘額為 0
        Wallet createdWallet = walletService.createWallet(createdUser.getId(), 0);

        //  User 和 Wallet 的組合
        return new UserWalletResponse(createdUser, createdWallet);
    }
}

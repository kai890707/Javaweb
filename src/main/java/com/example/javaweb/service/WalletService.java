package com.example.javaweb.service;

import com.example.javaweb.entity.User;
import com.example.javaweb.entity.Wallet;
import com.example.javaweb.repository.UserRepository;
import com.example.javaweb.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    /**
     * 尋找使用者錢包 By 錢包ID
     * @param id 錢包ID
     * @return 使用者錢包餘額
     */
    public Optional<Wallet> getWalletById(Long id) {
        return walletRepository.findById(id);
    }

    /**
     * 尋找使用者錢包 By 使用者ID
     * @param userId 使用者ID
     * @return Optional<Wallet>，可能為空
     */
    public Optional<Wallet> getWalletByUserId(Long userId) {
        return walletRepository.findByUserId(userId);
    }

    /**
     * 新增錢包餘額
     * @param userId 使用者ID
     * @param amount 金額
     */
    @Transactional
    public void addWallet(Long userId, Integer amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(()->new RuntimeException("Wallet not found"));

        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);
    }

    /**
     * 扣除錢包餘額
     * @param userId 使用者ID
     * @param amount 金額
     * @throws IllegalArgumentException 如果餘額不足
     */
    @Transactional
    public void reduceWallet(Long userId, Integer amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(()->new RuntimeException("Wallet not found"));

        // 檢查錢包餘額
        if (wallet.getBalance() + amount < 0) {
            throw new IllegalArgumentException("Not enough balance");
        }
        wallet.setBalance(wallet.getBalance() - amount);
        walletRepository.save(wallet);
    }

    /**
     * 創建錢包
     * @param userId 使用者 ID
     * @param initialBalance 初始餘額
     * @return 新建的 Wallet 實體
     * @throws IllegalArgumentException 如果使用者不存在或已經有錢包
     */
    @Transactional
    public Wallet createWallet(Long userId, Integer initialBalance){
        // 確認 User 是否存在
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        walletRepository.findByUserId(userId)
                .orElseThrow(()->new IllegalArgumentException("Wallet not found with user ID: " + userId));

        Wallet walletEntity = new Wallet();
        walletEntity.setUser(user);
        walletEntity.setBalance(initialBalance);

        return walletRepository.save(walletEntity);
    }

    /**
     * 刪除指定 userId 的錢包
     * @param userId 使用者 ID
     */
    @Transactional
    public void deleteWalletById(Long userId){
        walletRepository.deleteByUserId(userId);
    }
}

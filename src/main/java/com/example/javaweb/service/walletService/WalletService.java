package com.example.javaweb.service.walletService;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.entity.Wallet;
import com.example.javaweb.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class WalletService implements WalletServiceInterface {

    @Autowired
    WalletRepository walletRepository;

    /**
     * 查詢錢包(不含軟刪除資料)
     * @return
     */
    @Override
    public List<Wallet> getAllWallet() {
        return this.walletRepository.findAll();
    }

    /**
     * 查詢所有記錄（包括已刪除）
     * @return 錢包
     */
    public List<Wallet> getWalletsWithDeleted() {
        return this.walletRepository.findAllIncludingDeleted();
    }

    /**
     * 查詢已刪除的記錄
     * @return 使用者
     */
    public List<Wallet> getDeletedWallets() {
        return this.walletRepository.findDeleted();
    }

    /**
     * 取得錢包 with user id
     * @param userId 使用者id
     * @return 使用者資料
     * @throws ResourceNotFoundException
     */
    @Override
    public Wallet getWalletById(Long userId) throws ResourceNotFoundException {
        return this.walletRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id : " + userId));
    }

    /**
     * 新增使用者
     * @param wallet 錢包資料
     * @return 新增後的使用者實體
     */
    @Override
    public Wallet createWallet(Wallet wallet) {
        return this.walletRepository.save(wallet);
    }

    /**
     * 更新使用者資料
     * @param userId 使用者ID
     * @param wallet 錢包實體
     * @return 更新後的錢包實體
     * @throws ResourceNotFoundException
     */
    @Override
    public Wallet updateWallet(Long userId, Wallet wallet) throws ResourceNotFoundException {
        Wallet walletEntity = this.walletRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id : " + userId));
        walletEntity.setBalance(wallet.getBalance());
        return this.walletRepository.save(walletEntity);
    }

    /**
     * 刪除錢包
     * @param userId 使用者ID
     * @throws ResourceNotFoundException
     * @return 是否軟刪除成功
     */
    @Override
    public Boolean deleteWallet(Long userId) throws ResourceNotFoundException {
        Wallet walletEntity = this.walletRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for user id :: " + userId));
        walletEntity.setDeletedAt(LocalDateTime.now());
        LocalDateTime deleteTime = this.walletRepository.save(walletEntity).getDeletedAt();
        return deleteTime != null;
    }
}

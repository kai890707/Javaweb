package com.example.javaweb.repository;

import com.example.javaweb.entity.Payment;
import com.example.javaweb.entity.User;
import com.example.javaweb.entity.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    //根據user實體找wallet
    Optional<Wallet> findByUser(User user);

    //透過使用者ID找wallet
    Optional<Wallet> findByUserId(Long userId);

    // 刪除指定 userId 的 Wallet
    @Transactional
    @Modifying
    @Query("DELETE FROM Wallet w WHERE w.user.id = :userId")
    void deleteByUserId(Long userId);

    /**
     * 查詢所有記錄（包括已刪除）
     * @return
     */
    @Query("SELECT w FROM Wallet w")
    List<Wallet> findAllIncludingDeleted();

    /**
     * 查詢已刪除的記錄
     * @return
     */
    @Query("SELECT w FROM Wallet w WHERE w.deletedAt IS NOT NULL")
    List<Wallet> findDeleted();
}

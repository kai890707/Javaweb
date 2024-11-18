package com.example.javaweb.repository;

import com.example.javaweb.entity.Order;
import com.example.javaweb.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    /**
     * 查詢所有記錄（包括已刪除）
     * @return
     */
    @Query("SELECT pa FROM Payment pa")
    List<Payment> findAllIncludingDeleted();

    /**
     * 查詢已刪除的記錄
     * @return
     */
    @Query("SELECT pm FROM Payment pm WHERE pm.deletedAt IS NOT NULL")
    List<Payment> findDeleted();
}

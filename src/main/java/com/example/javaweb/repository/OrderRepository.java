package com.example.javaweb.repository;

import com.example.javaweb.entity.Order;
import com.example.javaweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository <Order, Long>{
    /**
     * 查詢所有記錄（包括已刪除）
     * @return
     */
    @Query("SELECT o FROM Order o")
    List<Order> findAllIncludingDeleted();

    /**
     * 查詢已刪除的記錄
     * @return
     */
    @Query("SELECT o FROM Order o WHERE o.deletedAt IS NOT NULL")
    List<Order> findDeleted();
}

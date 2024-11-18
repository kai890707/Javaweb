package com.example.javaweb.repository;

import com.example.javaweb.entity.Product;
import com.example.javaweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 查詢所有記錄（包括已刪除）
     * @return
     */
    @Query("SELECT u FROM User u")
    List<User> findAllIncludingDeleted();

    /**
     * 查詢已刪除的記錄
     * @return
     */
    @Query("SELECT u FROM User u WHERE u.deletedAt IS NOT NULL")
    List<User> findDeleted();
}

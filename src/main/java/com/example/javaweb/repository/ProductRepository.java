package com.example.javaweb.repository;

import com.example.javaweb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long> {

    /**
     * 根據產品名稱查詢產品列表
     *
     * @param name 產品名稱
     * @return List<Product> 查詢結果
     */
    Product findByName(String name);

    /**
     * 查詢所有記錄（包括已刪除）
     * @return
     */
    @Query("SELECT p FROM Product p")
    List<Product> findAllIncludingDeleted();

    /**
     * 查詢已刪除的記錄
     * @return
     */
    @Query("SELECT p FROM Product p WHERE p.deletedAt IS NOT NULL")
    List<Product> findDeleted();

}

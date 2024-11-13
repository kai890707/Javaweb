package com.example.javaweb.repository;

import com.example.javaweb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
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
    List<Product> findByName(String name);

}

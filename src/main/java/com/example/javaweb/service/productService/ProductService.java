package com.example.javaweb.service.productService;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.entity.Product;
import com.example.javaweb.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService implements ProductServiceInterface {

    @Autowired
    ProductRepository productRepository;

    /**
     * 查詢所有未刪除的記錄
     * @return 產品
     */
    @Override
    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    /**
     * 查詢所有記錄（包括已刪除）
     * @return 產品
     */
    public List<Product> getProductsWithDeleted() {
        return this.productRepository.findAllIncludingDeleted();
    }

    /**
     * 查詢已刪除的記錄
     * @return 產品
     */
    public List<Product> getDeletedProducts() {
        return this.productRepository.findDeleted();
    }

    @Override
    public List<Product> getProductsByName(String name) throws ResourceNotFoundException {
        return this.productRepository.findByName(name);
    }

    /**
     * 取得產品 with id
     * @param id 產品ID
     * @return 產品
     * @throws ResourceNotFoundException
     */
    @Override
    public Product getProductById(Long id) throws ResourceNotFoundException {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));
    }

    /**
     * 新增產品
     * @param product 產品資料
     * @return 新增後的產品實體
     */
    @Override
    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    /**
     * 更新產品
     * @param product 產品資料
     * @return 更新後的產品實體
     * @throws ResourceNotFoundException
     */
    @Override
    public Product updateProduct(Long id, Product product) throws ResourceNotFoundException {
        Product productEntity = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + product.getId()));
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(product.getPrice());
        return this.productRepository.save(productEntity);
    }

    /**
     * 刪除產品
     * @param id 產品ID
     * @throws ResourceNotFoundException
     * @return 是否軟刪除成功
     */
    @Override
    public Boolean deleteProduct(Long id) throws ResourceNotFoundException {
        Product productEntity = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));
        productEntity.setDeletedAt(LocalDateTime.now());
        LocalDateTime isDeleted = this.productRepository.save(productEntity).getDeletedAt();
        return isDeleted != null;
    }

}

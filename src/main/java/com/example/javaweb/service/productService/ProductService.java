package com.example.javaweb.service.productService;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.dto.request.ProductRequest;
import com.example.javaweb.dto.response.ProductResponse;
import com.example.javaweb.dto.response.UserResponse;
import com.example.javaweb.entity.Product;
import com.example.javaweb.entity.User;
import com.example.javaweb.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ProductService implements ProductServiceInterface {

    @Autowired
    ProductRepository productRepository;

    /**
     * 查詢所有未刪除的記錄
     * @return ProductResponse 產品
     */
    @Override
    public List<ProductResponse> getAllProduct() {
        List<Product> products = this.productRepository.findAll();
        return products.stream()
                .map(ProductResponse::new)
                .toList();
    }

    /**
     * 查詢所有記錄（包括已刪除）
     * @return ProductResponse 產品
     */
    public List<ProductResponse> getProductsWithDeleted() {
        List<Product> products = this.productRepository.findAllIncludingDeleted();
        return products.stream()
                .map(ProductResponse::new)
                .toList();
    }

    /**
     * 查詢已刪除的記錄
     * @return ProductResponse 產品
     */
    public List<ProductResponse> getDeletedProducts() {
        List<Product> products = this.productRepository.findDeleted();
        return products.stream()
                .map(ProductResponse::new)
                .toList();
    }

    @Override
    public ProductResponse getProductsByName(String name) throws ResourceNotFoundException {
        Product product = this.productRepository.findByName(name);
        return new ProductResponse(product);
    }

    /**
     * 取得產品 with id
     * @param id 產品ID
     * @return ProductResponse 產品
     * @throws ResourceNotFoundException
     */
    @Override
    public ProductResponse getProductById(Long id) throws ResourceNotFoundException {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));
        return new ProductResponse(product);
    }

    /**
     * 取得產品實體
     * @param id
     * @return Product
     * @throws ResourceNotFoundException
     */
    public Product getProductEntityById(Long id) throws ResourceNotFoundException {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + id));
    }


    /**
     * 新增產品
     * @param ProductRequest 產品資料
     * @return 新增後的產品實體
     */
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        if (productRequest.getName() == null || productRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name must not be null");
        }
        if (productRequest.getDescription() == null || productRequest.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Product description must not be null");
        }
        if (productRequest.getPrice() < 0 || productRequest.getPrice() == null) {
            throw new IllegalArgumentException("Product price must not be negative");
        }

        Product isProductExist = this.productRepository.findByName(productRequest.getName());
        if (isProductExist != null) {
            throw new IllegalArgumentException("Product already exists");
        }

        Product productEntity = new Product();
        productEntity.setName(productRequest.getName());
        productEntity.setDescription(productRequest.getDescription());
        productEntity.setPrice(productRequest.getPrice());
        Product created = this.productRepository.save(productEntity);
        return new ProductResponse(created);
    }

    /**
     * 更新產品
     * @param id 產品ID
     * @param ProductRequest 產品資料
     * @return 更新後的產品實體
     * @throws ResourceNotFoundException
     */
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) throws ResourceNotFoundException {
        Product productEntity = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));

        String name = Objects.requireNonNullElse(productRequest.getName(), productEntity.getName());
        String description = Objects.requireNonNullElse(productRequest.getDescription(), productEntity.getDescription());
        Integer price = Objects.requireNonNullElse(productRequest.getPrice(),productEntity.getPrice());
        productEntity.setName(name);
        productEntity.setDescription(description);
        productEntity.setPrice(price);
        productEntity = this.productRepository.save(productEntity);
        return new ProductResponse(productEntity);
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

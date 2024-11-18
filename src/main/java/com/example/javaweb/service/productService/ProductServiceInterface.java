package com.example.javaweb.service.productService;

import com.example.javaweb.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductServiceInterface {

    List<Product> getAllProduct();

    List<Product> getProductsWithDeleted();

    List<Product> getDeletedProducts();

    List<Product> getProductsByName(String name);

    Product getProductById(Long productId);

    Product createProduct(Product product);

    Product updateProduct(Long id, Product product);

    Boolean deleteProduct(Long id);
}

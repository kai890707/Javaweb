package com.example.javaweb.service.productService;

import com.example.javaweb.dto.request.ProductRequest;
import com.example.javaweb.dto.response.ProductResponse;
import com.example.javaweb.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductServiceInterface {

    List<ProductResponse> getAllProduct();

    List<ProductResponse> getProductsWithDeleted();

    List<ProductResponse> getDeletedProducts();

    ProductResponse getProductsByName(String name);

    ProductResponse getProductById(Long productId);

    ProductResponse createProduct(ProductRequest product);

    ProductResponse updateProduct(Long id, ProductRequest product);

    Boolean deleteProduct(Long id);
}

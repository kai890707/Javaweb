package com.example.javaweb.controller;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.dto.response.ApiResponse;
import com.example.javaweb.service.ErrorProcessor;
import com.example.javaweb.service.productService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.javaweb.entity.Product;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 取得所有產品
     * @return 包含所有產品的列表
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProduct();
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) throws ResourceNotFoundException {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    /**
     * 新增產品
     * @param product 要新增的產品資料
     * @return 新增成功的產品資料及 201 狀態
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createdProduct));
    }

    /**
     * 更新產品
     * @param id 要更新的產品 ID
     * @param product 更新後的產品資料
     * @return 成功更新的產品，或 404 錯誤狀態
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long id,@RequestBody Product product)  throws ResourceNotFoundException{
        Product afterUpdateProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(ApiResponse.success(afterUpdateProduct));
    }

    /**
     * 刪除產品
     * @param id 產品ID
     * @return 刪除通知
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long id) {
        Boolean isDeleted = productService.deleteProduct(id);
        if (!isDeleted) {
            ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(
                            HttpStatus.CONFLICT.value(),
                            "Product with id " + id + " can not be deleted"
                    ));
        }
        return ResponseEntity.ok(ApiResponse.success("Product ID : " + id + " was deleted."));
    }
}

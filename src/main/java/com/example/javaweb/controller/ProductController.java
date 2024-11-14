package com.example.javaweb.controller;

import com.example.javaweb.service.GenericService;
import com.example.javaweb.service.ProductService;
import com.example.javaweb.service.ErrorProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.javaweb.entity.Product;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 取得所有產品
     * @return 包含所有產品的列表
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (!product.isPresent()) {
            ErrorProcessor errorProcessor = new ErrorProcessor(
                    HttpStatus.NOT_FOUND.value(),
                    "Product not found",
                    "Product with ID " + id + " was not found."
            );
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorProcessor);
        }
        return ResponseEntity.ok(product.get());
    }

    /**
     * 新增產品
     * @param product 要新增的產品資料
     * @return 新增成功的產品資料及 201 狀態
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {

        Product createdProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * 更新產品
     * @param id 要更新的產品 ID
     * @param product 更新後的產品資料
     * @return 成功更新的產品，或 404 錯誤狀態
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id,@RequestBody Product product) {
        // 物件可能為空，因此用 Optional
        Optional<Product> existingProduct = productService.getProductById(id);
        if (!existingProduct.isPresent()) {
            ErrorProcessor errorProcessor = new ErrorProcessor(
                    HttpStatus.NOT_FOUND.value(),
                    "Product not found",
                    "Product with ID " + id + " was not found."
            );
           return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorProcessor);
        }

        Product currentProduct = existingProduct.get();
        if (product.getDescription() == null) {
            product.setDescription(currentProduct.getDescription());
        }
        if (product.getPrice() == null) {
            product.setPrice(currentProduct.getPrice());
        }
        if (product.getName() == null) {
            product.setName(currentProduct.getName());
        }

        product.setId(id);
        Product updateProdcut = productService.saveProduct(product);

        return ResponseEntity.ok(updateProdcut);
    }

    /**
     * 刪除產品
     * @param id 產品ID
     * @return 刪除通知
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        Optional<Product> existingProduct = productService.getProductById(id);
        if (!existingProduct.isPresent()) {
            ErrorProcessor errorProcessor = new ErrorProcessor(
                    HttpStatus.NOT_FOUND.value(),
                    "Product not found",
                    "Product with ID " + id + " was not found."
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorProcessor);
        }

        if (!productService.deleteProduct(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product with ID " + id + " not found.");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("Product with ID " + id + " has been deleted.");
    }
}

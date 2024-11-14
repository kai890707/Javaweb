package com.example.javaweb.service;

import com.example.javaweb.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.javaweb.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    /**
     * ProductRepository 的實例，用於執行產品的資料庫操作。
     */
    private final ProductRepository productRepository;
    private final GenericService genericService;

    /**
     * 依賴注入 ProductRepository。
     * @param productRepository ProductRepository 實例
     */
    @Autowired
    public ProductService(ProductRepository productRepository, GenericService genericService) {
        this.productRepository = productRepository;
        this.genericService = genericService;
    }

    /**
     * 取得所有產品。
     * @return 包含所有 Product 的列表
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * 根據產品 ID 取得單一產品。
     * @param id 產品唯一 ID
     * @return 包含 Product 的 Optional，若產品不存在則為空
     */
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * 新增或更新產品資料。
     * @param product 要儲存的產品實例
     * @return 儲存後的 Product 實例
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * 根據產品 ID 刪除產品。
     * @param id 產品唯一 ID
     */
    public boolean  deleteProduct(Long id) {
        return genericService.deleteEntityWithChecker(productRepository, id);
    }

    /**
     * 刪除產品並確認
     * @param id 產品唯一 ID
     * @return 布林值是否刪除成功
     */
    public boolean deleteProductWithChecker(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            return true;
        }
        return false;
    }

    /**
     * 根據產品名稱搜尋產品。
     * @param name 產品名稱
     * @return 與名稱匹配的 Product 列表
     */
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

}

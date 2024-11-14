package com.example.javaweb.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenericService {
    /**
     * 刪除產品並確認
     * @param id 產品唯一 ID
     * @return 布林值是否刪除成功
     */
    public <T, ID> boolean deleteEntityWithChecker(JpaRepository<T, ID> repository, ID id) {
        Optional<T> entity = repository.findById(id);
        if (entity.isPresent()) {
            repository.delete(entity.get());
            return true;
        }
        return false;
    }
}

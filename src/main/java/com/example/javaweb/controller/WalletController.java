package com.example.javaweb.controller;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.dto.response.ApiResponse;
import com.example.javaweb.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.javaweb.service.walletService.WalletService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    WalletService walletService;

    /**
     * 取得所有錢包
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Wallet>>> getAllWallet() {
        List<Wallet> walletList = walletService.getAllWallet();
        return ResponseEntity.ok(ApiResponse.success(walletList));
    }

    /**
     * 取得單一錢包
     * @param userId 使用者ID
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Wallet>> getWalletById(@PathVariable Long userId) throws ResourceNotFoundException {
        Wallet wallet = walletService.getWalletById(userId);
        return ResponseEntity.ok(ApiResponse.success(wallet));
    }

    /**
     * 建立錢包
     * @param wallet 錢包實體
     * @return 新增後的錢包實體
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Wallet>> createWallet(@RequestBody Wallet wallet) {
        Wallet createdWallet = walletService.createWallet(wallet);
        return ResponseEntity.ok(ApiResponse.success(createdWallet));
    }

    /**
     * 更新錢包
     * @param userId 使用者ID
     * @param wallet 錢包資料
     * @return 更新後的錢包實體
     * @throws ResourceNotFoundException
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Wallet>> updateWallet(@PathVariable Long userId, @RequestBody Wallet wallet) throws ResourceNotFoundException{
        Wallet walletUpdated = walletService.updateWallet(userId, wallet);
        return ResponseEntity.ok(ApiResponse.success(walletUpdated));
    }

    /**
     * 刪除錢包
     * @param userId 使用者ID
     * @return 刪除訊息
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteWallet(@PathVariable Long userId) throws ResourceNotFoundException {
        Boolean isWalletDeleted = walletService.deleteWallet(userId);
        if (!isWalletDeleted) {
           return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(
                            HttpStatus.CONFLICT.value(),
                            "User with user id " + userId + " can not be deleted"
                    ));
        }
        return ResponseEntity.ok(ApiResponse.success("Wallet with user ID " + userId + " has been deleted."));
    }
}

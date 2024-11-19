package com.example.javaweb.controller;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.dto.request.UserRequest;
import com.example.javaweb.dto.response.ApiResponse;
import com.example.javaweb.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.javaweb.service.userService.UserService;
import com.example.javaweb.service.walletService.WalletService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    /**
     * 取得所有User
     * @return 所有user
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> user = userService.getAllUser();
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    /**
     * 透過ID搜尋使用者
     * @param id 使用者ID
     * @return 單一使用者
     * @throws ResourceNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    /**
     * 新增使用者
     * @param user 新增前的使用者實體
     * @return 新增後的使用者實體
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUserWithWallet(@RequestBody UserRequest user) {
        UserResponse userResponse = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(userResponse));

    }

    /**
     * 更新使用者資料
     * @param id 使用者ID
     * @param UserRequest 使用者資料
     * @return 使用者更新後資料
     * @throws ResourceNotFoundException
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) throws ResourceNotFoundException {
        UserResponse userResponse = userService.updateUser(id,userRequest);
        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }

    /**
     * 刪除使用者
     * @param id 使用者ID
     * @return 刪除通知
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        Boolean isDeletedUser = userService.deleteUser(id);
        if (!isDeletedUser) {
            ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(
                            HttpStatus.CONFLICT.value(),
                            "User with id " + id + " can not be deleted"
                    ));
        }
        return ResponseEntity.ok(ApiResponse.success("User with ID " + id + " has been deleted."));
    }
}

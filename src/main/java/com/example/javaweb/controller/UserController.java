package com.example.javaweb.controller;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.dto.UserWalletResponse;
import com.example.javaweb.dto.response.ApiResponse;
import com.example.javaweb.entity.User;
import com.example.javaweb.service.ErrorProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.javaweb.service.userService.UserService;
import com.example.javaweb.service.walletService.WalletService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
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
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> user =  userService.getAllUser();
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    /**
     * 透過ID搜尋使用者
     * @param id 使用者ID
     * @return 單一使用者
     * @throws ResourceNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    /**
     * 新增使用者
     * @param user 新增前的使用者實體
     * @return 新增後的使用者實體
     */
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUserWithWallet(@RequestBody User user) {
        User userEntity = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(user));

    }

    /**
     * 更新使用者資料
     * @param id 使用者ID
     * @param user 使用者資料
     * @return 使用者更新後資料
     * @throws ResourceNotFoundException
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user) throws ResourceNotFoundException {
        User userEntity = userService.updateUser(id,user);
        return ResponseEntity.ok(ApiResponse.success(userEntity));
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

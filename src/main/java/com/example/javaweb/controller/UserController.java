package com.example.javaweb.controller;

import com.example.javaweb.dto.UserWalletResponse;
import com.example.javaweb.entity.Product;
import com.example.javaweb.entity.User;
import com.example.javaweb.entity.Wallet;
import com.example.javaweb.service.ErrorProcessor;
import com.example.javaweb.service.UserService;
import com.example.javaweb.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final WalletService walletService;

    @Autowired
    public UserController(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

    /**
     * 取得所有User
     * @return 所有user
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> user =  userService.findAll();
        return ResponseEntity.ok(user);
    }

    /**
     * 透過ID搜尋使用者
     * @param id 使用者ID
     * @return 單一使用者
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            ErrorProcessor errorProcessor = new ErrorProcessor(
                    HttpStatus.NOT_FOUND.value(),
                    "User not found",
                    "User with ID " + id + " was not found."
            );
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorProcessor);
        }
        return ResponseEntity.ok(user.get());
    }

    /**
     * 新增使用者
     * @param user 新增前的使用者實體
     * @return 新增後的使用者實體
     */
    @PostMapping("/{id}")
    public ResponseEntity<UserWalletResponse> createUserWithWallet(@RequestBody User user) {
        UserWalletResponse response = userService.createUserWithWallet(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            ErrorProcessor errorProcessor = new ErrorProcessor(
                    HttpStatus.NOT_FOUND.value(),
                    "User not found",
                    "User with ID " + id + " was not found."
            );
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorProcessor);
        }

        User currentUser = userOptional.get();
        user.setId(id);
        user.setAccount(currentUser.getAccount());
        //TODO: 密碼HASH未實現
        if (user.getPassword() == null) {
            user.setPassword(currentUser.getPassword());
        }

        User updateUser = userService.save(user);
        return ResponseEntity.ok(updateUser);
    }

    /**
     * 刪除使用者
     * @param id 使用者ID
     * @return 刪除通知
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            ErrorProcessor errorProcessor = new ErrorProcessor(
                    HttpStatus.NOT_FOUND.value(),
                    "User not found",
                    "User with ID " + id + " was not found."
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorProcessor);
        }
        return ResponseEntity.ok(user.get());
    }
}

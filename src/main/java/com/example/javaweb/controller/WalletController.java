//package com.example.javaweb.controller;
//
//import com.example.javaweb.entity.Wallet;
//import com.example.javaweb.service.ErrorProcessor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@CrossOrigin
//@RestController
//@RequestMapping("/api/wallet")
//public class WalletController {
//    private final WalletService1 walletService;
//
//    @Autowired
//    public WalletController(WalletService1 walletService) {
//        this.walletService = walletService;
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Object> getWalletById(@PathVariable Long id) {
//        Optional<Wallet> wallet = walletService.getWalletById(id);
//        if (!wallet.isPresent()) {
//            ErrorProcessor errorProcessor = new ErrorProcessor(
//                    HttpStatus.NOT_FOUND.value(),
//                    "User Wallet not found",
//                    "User Wallet with ID " + id + " was not found."
//            );
//            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(errorProcessor);
//        }
//        return ResponseEntity.ok(wallet.get());
//    }
//}

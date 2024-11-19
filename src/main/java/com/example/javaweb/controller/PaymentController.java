package com.example.javaweb.controller;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.dto.request.PaymentRequest;
import com.example.javaweb.dto.response.ApiResponse;
import com.example.javaweb.dto.response.PaymentResponse;
import com.example.javaweb.entity.Payment;
import com.example.javaweb.service.paymentService.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    /**
     * 取得所有結帳單
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {
        List<PaymentResponse> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(ApiResponse.success(payments));
    }

    /**
     * 取得單一帳單
     * @param id
     * @return 單一帳單
     * @throws ResourceNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(Long id) throws ResourceNotFoundException {
        PaymentResponse payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }

    /**
     * 新增訂單
     * @param payment 訂單資料
     * @return 新增後的訂單實體
     * @throws ResourceNotFoundException
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(@RequestBody PaymentRequest paymentRequest) throws ResourceNotFoundException {
        PaymentResponse createdPayment = paymentService.createPayment(paymentRequest);
        return ResponseEntity.ok(ApiResponse.success(createdPayment));
    }

    /**
     * 更新帳單
     * @param id 帳單ID
     * @param payment 帳單資料
     * @return 更新後的帳單實體
     * @throws ResourceNotFoundException
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePayment(@PathVariable Long id, @RequestBody PaymentRequest paymentRequest) throws ResourceNotFoundException {
        PaymentResponse updatedPayment = paymentService.updatePayment(id, paymentRequest);
        return ResponseEntity.ok(ApiResponse.success(updatedPayment));
    }

    /**
     * 刪除帳單
     * @param id 帳單ID
     * @return 是否刪除
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePayment(@PathVariable Long id) throws ResourceNotFoundException {
        Boolean deleted = paymentService.deletePayment(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(
                            HttpStatus.CONFLICT.value(),
                            "Payment with id " + id + " can not be deleted"
                    ));
        }
        return ResponseEntity.ok(ApiResponse.success("Payment ID : " + id + " was deleted."));
    }
}

package com.example.javaweb.service.paymentService;

import com.example.javaweb.dto.request.PaymentRequest;
import com.example.javaweb.dto.response.PaymentResponse;
import com.example.javaweb.entity.Payment;

import java.util.List;

public interface PaymentServiceInterface {
    List<PaymentResponse> getAllPayments();

    List<PaymentResponse> getPaymentsWithDeleted();

    List<PaymentResponse> getDeletedPayments();

    PaymentResponse getPaymentById(Long id);

    PaymentResponse createPayment(PaymentRequest payment);

    PaymentResponse updatePayment(Long id,PaymentRequest paymentRequest);

    Boolean deletePayment(Long id);
}

package com.example.javaweb.service.paymentService;

import com.example.javaweb.dto.request.PaymentRequest;
import com.example.javaweb.dto.response.PaymentResponse;
import com.example.javaweb.entity.Payment;

import java.util.List;

public interface PaymentServiceInterface {
    List<Payment> getAllPayments();

    List<Payment> getPaymentsWithDeleted();

    List<Payment> getDeletedPayments();

    Payment getPaymentById(Long id);

    PaymentResponse createPayment(PaymentRequest payment);

    PaymentResponse updatePayment(Long id,PaymentRequest paymentRequest);

    Boolean deletePayment(Long id);
}

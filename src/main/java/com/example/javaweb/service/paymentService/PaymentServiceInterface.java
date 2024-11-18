package com.example.javaweb.service.paymentService;

import com.example.javaweb.entity.Payment;

import java.util.List;

public interface PaymentServiceInterface {
    List<Payment> getAllPayments();

    List<Payment> getPaymentsWithDeleted();

    List<Payment> getDeletedPayments();

    Payment getPaymentById(Long id);

    Payment createPayment(Payment payment);

    Payment updatePayment(Payment payment);

    void deletePayment(Long id);
}

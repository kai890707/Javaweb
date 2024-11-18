package com.example.javaweb.service.paymentService;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.entity.Payment;
import com.example.javaweb.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PaymentService implements PaymentServiceInterface{

    @Autowired
    PaymentRepository paymentRepository;

    /**
     * 取得所有帳單(不含以軟刪除)
     * @return 結帳單
     */
    @Override
    public List<Payment> getAllPayments() {
        return this.paymentRepository.findAll();
    }

    /**
     * 取得所有帳單(含已刪除)
     * @return 帳單
     */
    @Override
    public List<Payment> getPaymentsWithDeleted() {
        return this.paymentRepository.findAllIncludingDeleted();
    }

    /**
     * 取得已軟刪除的帳單
     * @return 帳單
     */
    @Override
    public List<Payment> getDeletedPayments() {
        return this.paymentRepository.findDeleted();
    }

    /**
     * 取得帳蛋 with id
     * @param id 帳單ID
     * @return 帳單
     * @throws ResourceNotFoundException
     */
    @Override
    public Payment getPaymentById(Long id) throws ResourceNotFoundException {
        return this.paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    /**
     * 新增帳單
     * @param payment 帳單資料
     * @return 帳單
     */
    @Override
    public Payment createPayment(Payment payment) {
        return this.paymentRepository.save(payment);
    }

    /**
     * 更新帳單
     * @param payment 更新前的帳單
     * @return 更新後的帳單
     * @throws ResourceNotFoundException
     */
    @Override
    public Payment updatePayment(Payment payment) throws ResourceNotFoundException {
        Payment paymentEntity = this.paymentRepository.findById(payment.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        paymentEntity.setOrder(payment.getOrder());
        paymentEntity.setIsPaid(payment.getIsPaid());
        paymentEntity.setTotalAmount(payment.getTotalAmount());
        return this.paymentRepository.save(paymentEntity);
    }

    @Override
    public void deletePayment(Long id) throws ResourceNotFoundException {
        Payment paymentEntity = this.paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        paymentEntity.setDeletedAt(LocalDateTime.now());
        this.paymentRepository.save(paymentEntity);
    }
}

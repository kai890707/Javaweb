package com.example.javaweb.service.paymentService;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.dto.request.PaymentRequest;
import com.example.javaweb.dto.response.PaymentResponse;
import com.example.javaweb.entity.Order;
import com.example.javaweb.entity.Payment;
import com.example.javaweb.entity.Product;
import com.example.javaweb.repository.PaymentRepository;
import com.example.javaweb.service.orderService.OrderService;
import com.example.javaweb.service.productService.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentService implements PaymentServiceInterface{

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

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
    public PaymentResponse createPayment(PaymentRequest paymentRequest) throws ResourceNotFoundException,IllegalArgumentException {
        if (paymentRequest.getOrderId() == null) {
            throw new IllegalArgumentException("Order ID must not be null");
        }

        Optional<Payment> existingPayment = this.paymentRepository.findByOrderId(paymentRequest.getOrderId());
        if (existingPayment.isPresent()) {
            throw new IllegalArgumentException("Payment already exists for orderId: " + paymentRequest.getOrderId());
        }

        Order order = this.orderService.getOrderById(paymentRequest.getOrderId());
        Product product = this.productService.getProductById(order.getProduct().getId());

        Payment payment = new Payment();
        payment.setTotalAmount(product.getPrice() * order.getQuantity());
        payment.setIsPaid(false);
        payment.setOrder(order);

        Payment savedPayment = this.paymentRepository.save(payment);

        return new PaymentResponse(savedPayment);
    }

    /**
     * 更新帳單
     * @param payment 更新前的帳單
     * @return 更新後的帳單
     * @throws ResourceNotFoundException
     */
    @Override
    public PaymentResponse updatePayment(Long id, PaymentRequest paymentRequest) throws ResourceNotFoundException {
        Payment paymentEntity = this.paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        if (paymentRequest.getOrderId() == null) {
            paymentEntity.setIsPaid(paymentRequest.getIsPaid());
        } else {
            Order order = this.orderService.getOrderById(paymentRequest.getOrderId());
            Product product = this.productService.getProductById(order.getProduct().getId());

            if (order.getDeletedAt() != null) {
                throw new ResourceNotFoundException("Order deleted at is null");
            }

            paymentEntity.setOrder(order);
            paymentEntity.setIsPaid(paymentRequest.getIsPaid());
            paymentEntity.setTotalAmount(product.getPrice() * order.getQuantity());
        }

        Payment savedPayment = this.paymentRepository.save(paymentEntity);
        return new PaymentResponse(savedPayment);
    }

    /**
     * 刪除帳單
     * @param id 帳單ID
     * @return 是否刪除成功
     * @throws ResourceNotFoundException
     */
    @Override
    public Boolean deletePayment(Long id) throws ResourceNotFoundException {
        Payment paymentEntity = this.paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        paymentEntity.setDeletedAt(LocalDateTime.now());
        LocalDateTime deletedTime = this.paymentRepository.save(paymentEntity).getDeletedAt();
        return deletedTime != null;
    }
}

package com.example.javaweb.service.orderService;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.entity.Order;
import com.example.javaweb.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderService implements OrderServiceInterface {

    @Autowired
    OrderRepository orderRepository;

    /**
     * 取得所有訂單(不含以軟刪除)
     * @return 訂單
     */
    @Override
    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    /**
     * 取得所有訂單(含已刪除)
     * @return 訂單
     */
    @Override
    public List<Order> getOrdersWithDeleted() {
        return this.orderRepository.findAllIncludingDeleted();
    }

    /**
     * 取得已軟刪除的訂單
     * @return 訂單
     */
    @Override
    public List<Order> getDeletedOrders() {
        return this.orderRepository.findDeleted();
    }

    /**
     * 取得訂單 with id
     * @param orderId 訂單ID
     * @return 訂單
     * @throws ResourceNotFoundException
     */
    @Override
    public Order getOrderById(Long orderId) throws ResourceNotFoundException {
        return this.orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!"));
    }

    /**
     * 新增訂單
     * @param order 訂單資料
     * @return 新增後的訂單
     */
    @Override
    public Order createOrder(Order order) {
        return this.orderRepository.save(order);
    }

    /**
     * 更新訂單
     * @param order 訂單資料
     * @return 更新後的訂單
     * @throws ResourceNotFoundException
     */
    @Override
    public Order updateOrder(Order order) throws ResourceNotFoundException {
        Order orderEntity = this.orderRepository.findById(order.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!"));
        orderEntity.setProduct(order.getProduct());
        orderEntity.setQuantity(order.getQuantity());
        orderEntity.setUser(order.getUser());
        return this.orderRepository.save(orderEntity);
    }

    /**
     * 刪除訂單
     * @param id 訂單ID
     * @throws ResourceNotFoundException
     */
    @Override
    public void deleteOrder(Long id) throws ResourceNotFoundException {
        Order orderEntity = this.orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!"));
        orderEntity.setDeletedAt(LocalDateTime.now());
        this.orderRepository.save(orderEntity);
    }
}

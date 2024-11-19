package com.example.javaweb.service.orderService;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.dto.request.OrderRequest;
import com.example.javaweb.dto.response.OrderResponse;
import com.example.javaweb.entity.Order;
import com.example.javaweb.entity.Product;
import com.example.javaweb.entity.User;
import com.example.javaweb.repository.OrderRepository;
import com.example.javaweb.service.productService.ProductService;
import com.example.javaweb.service.userService.UserService;
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

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    /**
     * 取得所有訂單(不含以軟刪除)
     * @return 訂單
     */
    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = this.orderRepository.findAll();
        return orders.stream()
                .map(OrderResponse::new)
                .toList();
    }

    /**
     * 取得所有訂單(含已刪除)
     * @return 訂單
     */
    @Override
    public List<OrderResponse> getOrdersWithDeleted() {
        List<Order> orders = this.orderRepository.findAllIncludingDeleted();
        return orders.stream()
                .map(OrderResponse::new)
                .toList();
    }

    /**
     * 取得已軟刪除的訂單
     * @return 訂單
     */
    @Override
    public List<OrderResponse> getDeletedOrders() {
        List<Order> orders = this.orderRepository.findDeleted();
        return orders.stream()
                .map(OrderResponse::new)
                .toList();
    }

    /**
     * 取得order實體
     * @param id order id
     * @return order實體
     * @throws ResourceNotFoundException
     */
    public Order getOrderEntityById(Long id) throws ResourceNotFoundException {
        return this.orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!"));
    }

    /**
     * 取得訂單 with id
     * @param orderId 訂單ID
     * @return 訂單
     * @throws ResourceNotFoundException
     */
    @Override
    public OrderResponse getOrderById(Long orderId) throws ResourceNotFoundException {
        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!"));
        return new OrderResponse(order);
    }

    /**
     * 新增訂單
     * @param OrderResponse 訂單資料
     * @return 新增後的訂單
     * @throws ResourceNotFoundException
     */
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) throws ResourceNotFoundException {
        if (orderRequest.getProductId() == null || orderRequest.getUserId() == null) {
            throw new IllegalArgumentException("Product ID and User ID must not be null");
        }
        Product product = this.productService.getProductEntityById(orderRequest.getProductId());
        User user = this.userService.getUserEntityById(orderRequest.getUserId());

        Order order = new Order();
        order.setProduct(product);
        order.setUser(user);
        order.setQuantity(orderRequest.getQuantity());
        Order savedOrder = this.orderRepository.save(order);
        return new OrderResponse(savedOrder);
    }

    /**
     * 更新訂單
     * @param order 訂單資料
     * @return 更新後的訂單
     * @throws ResourceNotFoundException
     */
    @Override
    public OrderResponse updateOrder(Long id, OrderRequest orderRequest) throws ResourceNotFoundException {
        Order orderEntity = this.orderRepository.findById(orderRequest.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!"));
        Product product = this.productService.getProductEntityById(orderRequest.getProductId());
        User user = this.userService.getUserEntityById(orderRequest.getUserId());
        orderEntity.setProduct(product);
        orderEntity.setQuantity(orderRequest.getQuantity());
        orderEntity.setUser(user);
        Order updatedOrder = this.orderRepository.save(orderEntity);
        return new OrderResponse(updatedOrder);
    }

    /**
     * 刪除訂單
     * @param id 訂單ID
     * @return Boolean 是否刪除成功
     * @throws ResourceNotFoundException
     */
    @Override
    public Boolean deleteOrder(Long id) throws ResourceNotFoundException {
        Order orderEntity = this.orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!"));
        orderEntity.setDeletedAt(LocalDateTime.now());
        LocalDateTime isDeleted = this.orderRepository.save(orderEntity).getDeletedAt();
        return isDeleted != null;
    }
}

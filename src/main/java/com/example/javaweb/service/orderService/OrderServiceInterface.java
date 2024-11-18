package com.example.javaweb.service.orderService;
import com.example.javaweb.entity.Order;
import java.util.List;

public interface OrderServiceInterface {

    List<Order> getAllOrders();

    List<Order> getOrdersWithDeleted();

    List<Order> getDeletedOrders();

    Order getOrderById(Long orderId);

    Order createOrder(Order order);

    Order updateOrder(Order order);

    void deleteOrder(Long id);
}

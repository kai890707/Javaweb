package com.example.javaweb.service.orderService;
import com.example.javaweb.dto.request.OrderRequest;
import com.example.javaweb.entity.Order;
import java.util.List;

public interface OrderServiceInterface {

    List<Order> getAllOrders();

    List<Order> getOrdersWithDeleted();

    List<Order> getDeletedOrders();

    Order getOrderById(Long orderId);

    Order createOrder(OrderRequest order);

    Order updateOrder(Long id,Order order);

    Boolean deleteOrder(Long id);
}

package com.example.javaweb.service.orderService;
import com.example.javaweb.dto.request.OrderRequest;
import com.example.javaweb.dto.response.OrderResponse;
import com.example.javaweb.entity.Order;
import java.util.List;

public interface OrderServiceInterface {

    List<OrderResponse> getAllOrders();

    List<OrderResponse> getOrdersWithDeleted();

    List<OrderResponse> getDeletedOrders();

    OrderResponse getOrderById(Long orderId);

    OrderResponse createOrder(OrderRequest order);

    OrderResponse updateOrder(Long id,OrderRequest order);

    Boolean deleteOrder(Long id);
}

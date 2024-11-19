package com.example.javaweb.controller;

import com.example.javaweb.Exception.ResourceNotFoundException;
import com.example.javaweb.dto.request.OrderRequest;
import com.example.javaweb.dto.response.ApiResponse;
import com.example.javaweb.dto.response.OrderResponse;
import com.example.javaweb.entity.Order;
import com.example.javaweb.service.orderService.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    /**
     * 取得所有Order
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrder() {
        List<OrderResponse> orders = this.orderService.getAllOrders();
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    /**
     * 取得單一訂單
     * @param id 訂單ID
     * @return 訂單
     * @throws ResourceNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable Long id) throws ResourceNotFoundException {
        OrderResponse order = this.orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.success(order));
    }

    /**
     * 新增訂單
     * @param order 訂單
     * @return 新增後的訂單實體
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody OrderRequest orderRequest){
        OrderResponse orderEntity = this.orderService.createOrder(orderRequest);
        return ResponseEntity.ok(ApiResponse.success(orderEntity));
    }

    /**
     * 更新訂單
     * @param id 訂單ID
     * @param order 訂單資料
     * @return 更新後的訂單實體
     * @throws ResourceNotFoundException
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrder(@PathVariable Long id, @RequestBody OrderRequest order) throws ResourceNotFoundException {
        OrderResponse updatedOrder = this.orderService.updateOrder(id,order);
        return ResponseEntity.ok(ApiResponse.success(updatedOrder));
    }

    /**
     * 刪除訂單
     * @param id 訂單ID
     * @return 是否刪除成功String
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteOrder(@PathVariable Long id) throws ResourceNotFoundException {
        Boolean isDeletedOrder = this.orderService.deleteOrder(id);
        if (!isDeletedOrder) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(
                            HttpStatus.CONFLICT.value(),
                            "Order with id " + id + " can not be deleted"
                    ));
        }
        return ResponseEntity.ok(ApiResponse.success("Order ID : " + id + " was deleted."));
    }
}

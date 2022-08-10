package com.shopdashboardservice.controller;

import com.shopdashboardservice.model.Order;
import com.shopdashboardservice.model.requests.order.OrderAddUpdateRequest;
import com.shopdashboardservice.model.requests.order.OrderGetDeleteRequest;
import com.shopdashboardservice.model.requests.order.OrderListRequest;
import com.shopdashboardservice.model.responses.order.OrderAddUpdateResponse;
import com.shopdashboardservice.model.responses.order.OrderDeleteResponse;
import com.shopdashboardservice.model.responses.order.OrderGetResponse;
import com.shopdashboardservice.model.responses.order.OrderListResponse;
import com.shopdashboardservice.service.OrderService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/list")
    public ResponseEntity<OrderListResponse> getOrdersByFilter(@RequestBody @Valid OrderListRequest request) {
        List<Order> orders = orderService.getOrdersByFilter(request.getFilter());
        Integer totalRowCount = orderService.getTotalRowCount(request.getFilter());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new OrderListResponse()
                        .setFilter(request.getFilter())
                        .setOrders(orders)
                        .setTotalRowCount(totalRowCount));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<OrderGetResponse> getOrderByUUID(@Valid final OrderGetDeleteRequest request){
        Order order = orderService.getOrderByUUID(request.getUuid());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new OrderGetResponse()
                        .setOrder(order));
    }

    @PutMapping
    public ResponseEntity<OrderAddUpdateResponse> addOrder(@RequestBody @Valid OrderAddUpdateRequest request){
        Order order = orderService.addOrder(request.getOrder());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new OrderAddUpdateResponse()
                        .setOrder(order));
    }

    @PostMapping
    public ResponseEntity<OrderAddUpdateResponse> updateOrder(@RequestBody @Valid OrderAddUpdateRequest request){
        Order order = orderService.updateOrder(request.getOrder());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new OrderAddUpdateResponse()
                        .setOrder(order));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<OrderDeleteResponse> deleteOrder(@Valid OrderGetDeleteRequest request){
        orderService.deleteOrder(request.getUuid());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new OrderDeleteResponse()
                        .setUuid(request.getUuid()));
    }
}

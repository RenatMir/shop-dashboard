package com.shopdashboardservice.service;

import com.shopdashboardservice.model.Order;
import com.shopdashboardservice.model.listfilters.OrderListFilter;
import com.shopdashboardservice.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrdersByFilter(OrderListFilter filter) {
        return orderRepository.getOrdersByFilter(filter);
    }

    public Integer getTotalRowCount(OrderListFilter filter) {
        return orderRepository.getTotalRowCount(filter);
    }

    public Order getOrderByUUID(UUID uuid) {
        return orderRepository.getOrderByUUID(uuid);
    }

    public Order addOrder(Order order) {
        return orderRepository.addOrder(order);
    }

    public Order updateOrder(Order order) {
        return orderRepository.updateOrder(order);
    }

    public void deleteOrder(UUID uuid) {
        orderRepository.deleteOrder(uuid);
    }


}

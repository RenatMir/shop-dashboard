package com.shopdashboardservice.service;

import com.shopdashboardservice.model.Order;
import com.shopdashboardservice.model.listfilters.OrderListFilter;
import com.shopdashboardservice.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "transactionManager")
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getOrdersByFilter(OrderListFilter filter) {
        return orderRepository.getOrdersByFilter(filter);
    }

    public Integer getTotalRowCount(OrderListFilter filter) {
        return orderRepository.getTotalRowCount(filter);
    }

    public Order getOrderByUUID(UUID uuid) {
        return orderRepository.getOrderByUUID(uuid);
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager")
    public Order addOrder(Order order) {
        return orderRepository.addOrder(order);
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager")
    public Order updateOrder(Order order) {
        return orderRepository.updateOrder(order);
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager")
    public void deleteOrder(UUID uuid) {
        orderRepository.deleteOrder(uuid);
    }


}

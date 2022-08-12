package com.shopdashboardservice.endpoints;

import com.shopdashboardservice.AbstractRestTest;
import com.shopdashboardservice.model.ErrorResponse;
import com.shopdashboardservice.model.Order;
import com.shopdashboardservice.model.exception.AppException.ErrorCode;
import com.shopdashboardservice.model.listfilters.OrderListFilter;
import com.shopdashboardservice.model.requests.order.OrderAddUpdateRequest;
import com.shopdashboardservice.model.requests.order.OrderListRequest;
import com.shopdashboardservice.model.responses.order.OrderAddUpdateResponse;
import com.shopdashboardservice.model.responses.order.OrderGetResponse;
import com.shopdashboardservice.model.responses.order.OrderListResponse;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional("transactionManager")
public class OrderEndpointsTest extends AbstractRestTest {

    @Test
    @SneakyThrows
    public void orderListTest() {
        OrderListFilter filter = new OrderListFilter();
        OrderListRequest request = new OrderListRequest().setFilter(filter);

        OrderListResponse response = getListRequest(
                Endpoint.orderList,
                MAPPER.writeValueAsString(request),
                OrderListResponse.class
        );

        assertEquals(1, response.getOrders().size());
    }

    @Test
    public void orderListErrorTest() {
        ErrorResponse response = getRequest(
            Endpoint.orderList,
            ErrorResponse.class
        );

        assertEquals(ErrorCode.TECHNICAL_ERROR, response.getErrorCode());
    }

    @Test
    @SneakyThrows
    public void orderGetTest() {
        UUID uuid = UUID.randomUUID();
        Order order = new Order()
                .setUuid(uuid)
                .setClientName("TEST_CLIENT")
                .setOrderAmount(55.5)
                .setConfig("{}");
        OrderAddUpdateRequest orderAddRequest = new OrderAddUpdateRequest().setOrder(order);

        OrderAddUpdateResponse orderAddResponse = putRequest(
                Endpoint.order,
                MAPPER.writeValueAsString(orderAddRequest),
                OrderAddUpdateResponse.class
        );

        assertEquals(uuid, orderAddResponse.getOrder().getUuid());

        OrderGetResponse orderGetResponse = getRequest(
                Endpoint.order,
                uuid,
                OrderGetResponse.class
        );

        assertEquals(uuid, orderGetResponse.getOrder().getUuid());
    }

    @Test
    @SneakyThrows
    public void orderAddTest() {
        UUID uuid = UUID.randomUUID();
        Order order = new Order()
                .setUuid(uuid)
                .setClientName("TEST_CLIENT")
                .setOrderAmount(55.5)
                .setConfig("{\"test\" : \"value\"}");
        OrderAddUpdateRequest request = new OrderAddUpdateRequest().setOrder(order);

        OrderAddUpdateResponse response = putRequest(
                Endpoint.order,
                MAPPER.writeValueAsString(request),
                OrderAddUpdateResponse.class
        );

        assertEquals(uuid, response.getOrder().getUuid());
    }

    @Test
    @SneakyThrows
    public void orderUpdateTest() {
        UUID uuid = UUID.randomUUID();
        Order order = new Order()
                .setUuid(uuid)
                .setClientName("TEST_CLIENT")
                .setOrderAmount(55.5)
                .setConfig("{}");
        OrderAddUpdateRequest orderAddRequest = new OrderAddUpdateRequest().setOrder(order);

        OrderAddUpdateResponse orderAddResponse = putRequest(
                Endpoint.order,
                MAPPER.writeValueAsString(orderAddRequest),
                OrderAddUpdateResponse.class
        );

        assertEquals(uuid, orderAddResponse.getOrder().getUuid());


        OrderAddUpdateRequest orderUpdateRequest = new OrderAddUpdateRequest().setOrder(order.setClientName("TEST_CLIENT_2"));
        OrderAddUpdateResponse orderUpdateResponse = postRequest(
                Endpoint.order,
                MAPPER.writeValueAsString(orderUpdateRequest),
                OrderAddUpdateResponse.class
        );

        assertEquals("TEST_CLIENT_2", orderUpdateResponse.getOrder().getClientName());
    }
}

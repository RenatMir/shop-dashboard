package com.shopdashboardservice.controller;

import com.shopdashboardservice.AbstractRestTest;
import com.shopdashboardservice.model.responses.product.ProductGetResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderControllerTest extends AbstractRestTest {

    @Test
    public void orderListTest() {
        ProductGetResponse response = getRequest(
                Endpoint.product,
                1,
                ProductGetResponse.class
        );
        assertEquals(response.getProduct().getId(), 1);
    }
}

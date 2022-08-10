package com.shopdashboardservice.controller;

import com.shopdashboardservice.AbstractRestTest;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class PingControllerTest extends AbstractRestTest {

    @Test
    public void pingTest() {
        String response = getRequest(Endpoint.ping, String.class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("pong", response);
    }
}

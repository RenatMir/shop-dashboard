package com.shopdashboardservice.controller;

import com.shopdashboardservice.AbstractRestTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PingControllerTest extends AbstractRestTest {

    @Test
    public void pingTest() {
        String response = getRequest(Endpoint.ping, String.class);

        assertNotNull(response);
        assertEquals("pong", response);
    }
}

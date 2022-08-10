package com.shopdashboardservice.model.responses.order;

import com.shopdashboardservice.model.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OrderGetResponse {

    private Order order;
}

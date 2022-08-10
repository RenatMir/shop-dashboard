package com.shopdashboardservice.model.responses.order;

import com.shopdashboardservice.model.Order;
import com.shopdashboardservice.model.listfilters.OrderListFilter;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OrderListResponse {

    private OrderListFilter filter;

    private List<Order> orders;

    private Integer totalRowCount;
}

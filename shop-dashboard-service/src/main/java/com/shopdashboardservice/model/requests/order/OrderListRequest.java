package com.shopdashboardservice.model.requests.order;

import com.shopdashboardservice.model.listfilters.OrderListFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OrderListRequest {

    private OrderListFilter filter;
}

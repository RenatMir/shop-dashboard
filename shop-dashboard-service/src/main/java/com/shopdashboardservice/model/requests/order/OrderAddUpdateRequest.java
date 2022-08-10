package com.shopdashboardservice.model.requests.order;

import com.shopdashboardservice.model.Order;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OrderAddUpdateRequest {

    @Valid
    @NotNull
    private Order order;
}

package com.shopdashboardservice.model.requests.product;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductGetDeleteResponse {

    @PositiveOrZero
    @NotNull
    private Long id;
}

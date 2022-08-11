package com.shopdashboardservice.model.requests.producttosell;

import com.shopdashboardservice.model.ProductToSell;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductToSellAddUpdateRequest {

    @Valid
    @NotNull
    private ProductToSell product;
}

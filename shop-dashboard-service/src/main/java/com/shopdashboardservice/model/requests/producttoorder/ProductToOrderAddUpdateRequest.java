package com.shopdashboardservice.model.requests.producttoorder;

import com.shopdashboardservice.model.ProductToOrder;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductToOrderAddUpdateRequest {

    @Valid
    @NotNull
    private ProductToOrder product;
}

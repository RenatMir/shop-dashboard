package com.shopdashboardservice.model.requests.product;

import com.shopdashboardservice.model.Product;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductAddUpdateRequest {

    @Valid
    @NotNull
    private Product product;
}

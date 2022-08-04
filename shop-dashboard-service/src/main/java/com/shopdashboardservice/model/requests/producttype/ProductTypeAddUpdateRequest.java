package com.shopdashboardservice.model.requests.producttype;

import com.shopdashboardservice.model.ProductType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductTypeAddUpdateRequest {

    @Valid
    @NotNull
    private ProductType productType;
}

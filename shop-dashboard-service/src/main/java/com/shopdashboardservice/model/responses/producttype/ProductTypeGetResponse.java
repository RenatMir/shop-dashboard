package com.shopdashboardservice.model.responses.producttype;

import com.shopdashboardservice.model.ProductType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductTypeGetResponse {

    private ProductType productType;
}

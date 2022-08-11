package com.shopdashboardservice.model.responses.producttosell;

import com.shopdashboardservice.model.ProductToSell;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductToSellAddUpdateResponse {

    private ProductToSell product;
}

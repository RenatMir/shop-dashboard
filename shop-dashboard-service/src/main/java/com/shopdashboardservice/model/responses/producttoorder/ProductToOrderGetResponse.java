package com.shopdashboardservice.model.responses.producttoorder;

import com.shopdashboardservice.model.ProductToOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductToOrderGetResponse {

    private ProductToOrder product;
}

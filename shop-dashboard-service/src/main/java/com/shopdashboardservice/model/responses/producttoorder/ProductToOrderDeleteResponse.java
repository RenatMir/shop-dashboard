package com.shopdashboardservice.model.responses.producttoorder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductToOrderDeleteResponse {

    private String productName;
}

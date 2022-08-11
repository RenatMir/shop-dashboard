package com.shopdashboardservice.model.responses.producttosell;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductToSellDeleteResponse {

    private String productName;
}

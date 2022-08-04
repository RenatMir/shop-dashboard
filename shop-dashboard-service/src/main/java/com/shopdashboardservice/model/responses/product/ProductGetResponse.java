package com.shopdashboardservice.model.responses.product;

import com.shopdashboardservice.model.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductGetResponse {

    private Product product;
}

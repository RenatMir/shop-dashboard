package com.shopdashboardservice.model.responses.product;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductDeleteResponse {

    private String name;
}

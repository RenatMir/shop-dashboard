package com.shopdashboardservice.model.requests.product;

import com.shopdashboardservice.model.ProductListFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductListRequest {

    private ProductListFilter filter;
}

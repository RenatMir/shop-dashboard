package com.shopdashboardservice.model.requests.producttosell;

import com.shopdashboardservice.model.listfilters.ProductToSellListFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductToSellListRequest {

    private ProductToSellListFilter filter;
}

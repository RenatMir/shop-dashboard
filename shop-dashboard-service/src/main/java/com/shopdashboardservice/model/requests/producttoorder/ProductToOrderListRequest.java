package com.shopdashboardservice.model.requests.producttoorder;

import com.shopdashboardservice.model.listfilters.ProductToOrderListFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductToOrderListRequest {

    private ProductToOrderListFilter filter;
}

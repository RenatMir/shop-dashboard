package com.shopdashboardservice.model.responses.producttoorder;

import com.shopdashboardservice.model.ProductToOrder;
import com.shopdashboardservice.model.listfilters.ProductToOrderListFilter;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductToOrderListResponse {

    private ProductToOrderListFilter filter;

    private List<ProductToOrder> products;

    private Integer totalRowCount;
}

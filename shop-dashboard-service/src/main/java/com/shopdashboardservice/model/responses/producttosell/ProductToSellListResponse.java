package com.shopdashboardservice.model.responses.producttosell;

import com.shopdashboardservice.model.ProductToSell;
import com.shopdashboardservice.model.listfilters.ProductToSellListFilter;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductToSellListResponse {

    private ProductToSellListFilter filter;

    private List<ProductToSell> products;

    private Integer totalRowCount;
}

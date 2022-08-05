package com.shopdashboardservice.model.responses.product;

import com.shopdashboardservice.model.Product;
import com.shopdashboardservice.model.listfilters.ProductListFilter;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductListResponse {

    private ProductListFilter filter;

    private List<Product> products;

    private Integer totalRowCount;
}

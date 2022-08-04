package com.shopdashboardservice.model.responses.producttype;

import com.shopdashboardservice.model.ProductType;
import com.shopdashboardservice.model.ProductTypesListFilter;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductTypeListResponse {

    private ProductTypesListFilter filter;

    private List<ProductType> productTypes;

    private Integer totalRowCount;
}

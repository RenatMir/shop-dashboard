package com.shopdashboardservice.model.requests.producttype;

import com.shopdashboardservice.model.ProductTypesListFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductTypeListRequest {

    private ProductTypesListFilter filter;
}

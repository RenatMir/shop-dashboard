package com.shopdashboardservice.model.listfilters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopdashboardservice.model.AbstractFilter;
import com.shopdashboardservice.model.OrderValueProvider;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductToOrderListFilter extends AbstractFilter {

    private String productName;

    private Double price;

    private List<ProductToOrderListFilter.ORDER_BY> orderBy;

    @JsonIgnore
    public String getOrderBySql() {
        return getOrderBySql(getOrderBy());
    }

    @Getter
    private enum ORDER_BY implements OrderValueProvider {
        productNameAsc("product_name asc"),
        productNameDesc("product_name desc"),
        priceAsc("price asc"),
        priceDesc("price desc");

        private final String value;

        ORDER_BY(String value) {
            this.value = value;
        }
    }

    public enum FILTER_FIELDS {
        productName,
        price,
        offset,
        limit
    }
}

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
public class ProductListFilter extends AbstractFilter {

    private Long id;

    private String name;

    private String productType;

    private Integer expirationDays;

    private List<ORDER_BY> orderBy;

    @JsonIgnore
    public String getOrderBySql() {
        return getOrderBySql(getOrderBy());
    }

    @Getter
    private enum ORDER_BY implements OrderValueProvider {
        nameAsc("name asc"),
        nameDesc("name desc"),
        productTypeAsc("product_type asc"),
        productTypeDesc("product_type desc");

        private final String value;

        ORDER_BY(String value) {
            this.value = value;
        }
    }

    public enum FILTER_FIELDS {
        id,
        name,
        productType,
        expirationDays,
        offset,
        limit
    }
}

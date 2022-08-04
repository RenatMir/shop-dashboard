package com.shopdashboardservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Double price;

    private Integer expirationDays;

    private List<ProductListFilter.ORDER_BY> orderBy;

    @JsonIgnore
    public String getOrderBySql() {
        return AbstractFilter.getOrderBySql(getOrderBy());
    }

    @Getter
    private enum ORDER_BY implements OrderValueProvider {
        idAsc("id asc"),
        idDesc("id desc"),
        nameAsc("name asc"),
        nameDesc("name desc"),
        productTypeAsc("product_type asc"),
        productTypeDesc("product_type desc"),
        priceAsc("price asc"),
        priceDesc("price desc"),
        expirationDaysAsc("expiration_days asc"),
        expirationDaysDesc("expiration_days desc");

        private final String value;

        ORDER_BY(String value) {
            this.value = value;
        }
    }

    public enum FILTER_FIELDS {
        id,
        name,
        productType,
        price,
        expirationDays,
        offset,
        limit
    }
}

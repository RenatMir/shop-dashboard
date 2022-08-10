package com.shopdashboardservice.model.listfilters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopdashboardservice.model.AbstractFilter;
import com.shopdashboardservice.model.OrderValueProvider;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class ProductTypeListFilter extends AbstractFilter {

    private String type;

    private List<ORDER_BY> orderBy;

    @JsonIgnore
    public String getOrderBySql() {
        return getOrderBySql(getOrderBy());
    }

    @Getter
    private enum ORDER_BY implements OrderValueProvider {
        typeAsc("type asc"),
        typeDesc("type desc");

        private final String value;

        ORDER_BY(String value) {
            this.value = value;
        }
    }

    public enum FILTER_FIELDS {
        id,
        type,
        offset,
        limit
    }
}

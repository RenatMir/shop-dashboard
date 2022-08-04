package com.shopdashboardservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class ProductTypesListFilter extends AbstractFilter {

    private Long id;
    private String type;

    private List<ORDER_BY> orderBy;

    @JsonIgnore
    public String getOrderBySql() {
        return AbstractFilter.getOrderBySql(getOrderBy());
    }

    @Getter
    private enum ORDER_BY implements OrderValueProvider {
        idAsc("id asc"),
        idDesc("id desc"),
        typeAsc("type asc"),
        typeDesc("type desc");

        private final String value;

        ORDER_BY(String value) {
            this.value = value;
        }
    }

    public enum FILTER_FIELDS {
        type,
        offset,
        limit
    }
}

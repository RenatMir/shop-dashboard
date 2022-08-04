package com.shopdashboardservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class AbstractFilter {

    private int pageSize;

    private int pageNumber;

    @JsonIgnore
    protected static <ORDER_BY extends Enum<ORDER_BY> & OrderValueProvider> String getOrderBySql(List<ORDER_BY> orderBy) {
        if (orderBy == null || orderBy.isEmpty()) {
            return "";
        }

        StringBuilder orderByFields = new StringBuilder();
        for (ORDER_BY each : orderBy) {
            if (orderByFields.length() > 0)
                orderByFields.append(",");
            orderByFields.append(each.getValue());
        }
        return " ORDER BY " + orderByFields;
    }

    @JsonIgnore
    public boolean isPageable() {
        return pageSize > 0;
    }
}

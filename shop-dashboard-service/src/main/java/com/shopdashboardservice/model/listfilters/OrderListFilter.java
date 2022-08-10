package com.shopdashboardservice.model.listfilters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopdashboardservice.model.AbstractFilter;
import com.shopdashboardservice.model.OrderValueProvider;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OrderListFilter extends AbstractFilter {

    private UUID uuid;

    private Double orderAmount;

    private String clientName;

    private Instant orderDate;

    private List<ORDER_BY> orderBy;

    @JsonIgnore
    public String getOrderBySql() {
        return getOrderBySql(getOrderBy());
    }

    @Getter
    private enum ORDER_BY implements OrderValueProvider {
        orderDateAsc("order_date asc"),
        orderDateDesc("order_date desc"),
        clientNameAsc("client_name asc"),
        clientNameDesc("client_name desc"),
        orderAmountAsc("name asc"),
        orderAmountDesc("name desc");

        private final String value;

        ORDER_BY(String value) {
            this.value = value;
        }
    }

    public enum FILTER_FIELDS {
        uuid,
        orderAmount,
        clientName,
        orderDate,
        offset,
        limit
    }

}

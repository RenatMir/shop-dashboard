package com.shopdashboardservice.model;

import com.shopdashboardservice.validation.JsonValidation;
import java.sql.Timestamp;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AbstractEntity {

    private UUID uuid;

    @NotNull
    @Positive
    private Double orderAmount;

    private Timestamp orderDate;

    @NotNull
    private String clientName;

    @JsonValidation
    private String config;
}

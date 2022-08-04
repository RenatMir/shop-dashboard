package com.shopdashboardservice.model;

import java.time.Instant;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public abstract class AbstractEntity {

    @PositiveOrZero
    private Integer version;

    private Instant lastChangeDate;
}

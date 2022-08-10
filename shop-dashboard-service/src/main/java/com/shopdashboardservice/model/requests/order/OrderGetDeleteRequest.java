package com.shopdashboardservice.model.requests.order;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OrderGetDeleteRequest {

    @NotNull
    private UUID uuid;
}

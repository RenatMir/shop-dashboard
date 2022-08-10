package com.shopdashboardservice.model.responses.order;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OrderDeleteResponse {

    private UUID uuid;
}

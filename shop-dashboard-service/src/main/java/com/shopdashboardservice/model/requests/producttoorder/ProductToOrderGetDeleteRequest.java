package com.shopdashboardservice.model.requests.producttoorder;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductToOrderGetDeleteRequest {

    @NotBlank
    private String productName;
}

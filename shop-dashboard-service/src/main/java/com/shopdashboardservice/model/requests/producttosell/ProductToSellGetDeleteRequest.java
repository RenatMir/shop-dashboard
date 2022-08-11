package com.shopdashboardservice.model.requests.producttosell;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductToSellGetDeleteRequest {

    @NotBlank
    private String productName;
}

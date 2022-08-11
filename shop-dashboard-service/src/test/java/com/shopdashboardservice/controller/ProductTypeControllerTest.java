package com.shopdashboardservice.controller;

import com.shopdashboardservice.AbstractRestTest;
import com.shopdashboardservice.model.ErrorResponse;
import com.shopdashboardservice.model.ProductType;
import com.shopdashboardservice.model.exception.AppException.ErrorCode;
import com.shopdashboardservice.model.listfilters.ProductTypeListFilter;
import com.shopdashboardservice.model.requests.producttype.ProductTypeAddRequest;
import com.shopdashboardservice.model.requests.producttype.ProductTypeListRequest;
import com.shopdashboardservice.model.responses.producttype.ProductTypeAddResponse;
import com.shopdashboardservice.model.responses.producttype.ProductTypeDeleteResponse;
import com.shopdashboardservice.model.responses.producttype.ProductTypeGetResponse;
import com.shopdashboardservice.model.responses.producttype.ProductTypeListResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional("transactionManager")
public class ProductTypeControllerTest extends AbstractRestTest {

    @Test
    @SneakyThrows
    public void productTypeListTest() {
        ProductTypeListFilter filter = new ProductTypeListFilter();
        ProductTypeListRequest request = new ProductTypeListRequest().setFilter(filter);

        ProductTypeListResponse response = postRequest(
                Endpoint.productTypeList,
                MAPPER.writeValueAsString(request),
                ProductTypeListResponse.class
        );

        assertEquals(1, response.getTotalRowCount());
        assertEquals(1, response.getProductTypes().size());
    }

    @Test
    public void productTypeListErrorTest() {
        ErrorResponse response = getRequest(
                Endpoint.productTypeList,
                ErrorResponse.class
        );

        assertEquals(ErrorCode.TECHNICAL_ERROR, response.getErrorCode());
    }
    @Test
    @SneakyThrows
    public void productTypeGetTest() {
        ProductTypeGetResponse response = getRequest(
                Endpoint.productType,
                "Drink",
                ProductTypeGetResponse.class
        );

        assertEquals("Drink", response.getProductType().getType());
    }

    @Test
    @SneakyThrows
    public void productTypeGetByErrorTypeTest() {
        ProductTypeGetResponse response = getRequest(
                Endpoint.productType,
                "INVALID_TYPE",
                ProductTypeGetResponse.class
        );

        assertNull(response.getProductType());
    }

    @Test
    @SneakyThrows
    public void productTypeAddTest() {
        ProductType productType = new ProductType().setType("TEST_TYPE");
        ProductTypeAddRequest request = new ProductTypeAddRequest().setProductType(productType);

        ProductTypeAddResponse response = putRequest(
                Endpoint.productType,
                MAPPER.writeValueAsString(request),
                ProductTypeAddResponse.class
        );

        assertEquals(productType.getType(), response.getProductType().getType());
    }

    @Test
    @SneakyThrows
    public void productTypeDeleteTest() {
        String productTypeName = "TEST_TYPE";

        ProductType productType = new ProductType().setType(productTypeName);
        ProductTypeAddRequest request = new ProductTypeAddRequest().setProductType(productType);

        ProductTypeAddResponse addResponse = putRequest(
                Endpoint.productType,
                MAPPER.writeValueAsString(request),
                ProductTypeAddResponse.class
        );
        assertEquals(productType.getType(), addResponse.getProductType().getType());

        ProductTypeDeleteResponse deleteResponse = deleteRequest(
                Endpoint.productType,
                productTypeName,
                ProductTypeDeleteResponse.class
        );

        assertEquals(productTypeName, deleteResponse.getType());
    }
}

package com.shopdashboardservice.endpoints;

import com.shopdashboardservice.AbstractRestTest;
import com.shopdashboardservice.model.ErrorResponse;
import com.shopdashboardservice.model.Product;
import com.shopdashboardservice.model.ProductType;
import com.shopdashboardservice.model.exception.AppException.ErrorCode;
import com.shopdashboardservice.model.listfilters.ProductListFilter;
import com.shopdashboardservice.model.requests.product.ProductAddUpdateRequest;
import com.shopdashboardservice.model.requests.product.ProductListRequest;
import com.shopdashboardservice.model.requests.producttype.ProductTypeAddRequest;
import com.shopdashboardservice.model.responses.product.ProductAddUpdateResponse;
import com.shopdashboardservice.model.responses.product.ProductGetResponse;
import com.shopdashboardservice.model.responses.product.ProductListResponse;
import com.shopdashboardservice.model.responses.producttype.ProductTypeAddResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional("transactionManager")
public class ProductEndpointsTest extends AbstractRestTest {

    @Test
    @SneakyThrows
    public void productListTest() {
        ProductListFilter filter = new ProductListFilter();
        ProductListRequest request = new ProductListRequest().setFilter(filter);

        ProductListResponse response = postRequest(
                Endpoint.productList,
                MAPPER.writeValueAsString(request),
                ProductListResponse.class
        );

        assertEquals(2, response.getTotalRowCount());
        assertEquals(2, response.getProducts().size());
    }

    @Test
    public void productListErrorTest() {
        ErrorResponse response = getRequest(
                Endpoint.productList,
                ErrorResponse.class
        );

        assertEquals(ErrorCode.TECHNICAL_ERROR, response.getErrorCode());
    }

    @Test
    public void productGetTest() {
        ProductGetResponse response = getRequest(
                Endpoint.product,
                "Coca-Cola 2L",
                ProductGetResponse.class
        );

        assertEquals("Coca-Cola 2L", response.getProduct().getName());
    }

    @Test
    public void productGetErrorTest() {
        ProductGetResponse response = getRequest(
                Endpoint.product,
                "INVALID_PRODUCT",
                ProductGetResponse.class
        );

        assertNull(response.getProduct());
    }

    @Test
    @SneakyThrows
    public void productAddTest() {
        ProductType productType = new ProductType().setType("TEST_TYPE");
        ProductTypeAddRequest productTypeAddRequest = new ProductTypeAddRequest().setProductType(productType);

        ProductTypeAddResponse productTypeAddResponse = putRequest(
                Endpoint.productType,
                MAPPER.writeValueAsString(productTypeAddRequest),
                ProductTypeAddResponse.class
        );

        assertEquals(productType.getType(), productTypeAddResponse.getProductType().getType());

        Product product = new Product()
                .setName("TEST_PRODUCT")
                .setProductType("TEST_TYPE")
                .setExpirationDays(30);
        ProductAddUpdateRequest productAddUpdateRequest = new ProductAddUpdateRequest().setProduct(product);

        ProductAddUpdateResponse productAddUpdateResponse = putRequest(
                Endpoint.product,
                MAPPER.writeValueAsString(productAddUpdateRequest),
                ProductAddUpdateResponse.class
        );

        assertEquals(product.getName(), productAddUpdateResponse.getProduct().getName());
    }

//    @Test
//    @SneakyThrows
//    public void productUpdateTest() {
//        ProductType productType = new ProductType().setType("TEST_TYPE");
//        ProductTypeAddRequest productTypeAddRequest = new ProductTypeAddRequest().setProductType(productType);
//
//        ProductTypeAddResponse productTypeAddResponse = putRequest(
//                Endpoint.productType,
//                MAPPER.writeValueAsString(productTypeAddRequest),
//                ProductTypeAddResponse.class
//        );
//
//        assertEquals(productType.getType(), productTypeAddResponse.getProductType().getType());
//
//        Product product = new Product()
//                .setName("TEST_PRODUCT")
//                .setProductType("TEST_TYPE")
//                .setExpirationDays(30);
//        ProductAddUpdateRequest productAddUpdateRequest = new ProductAddUpdateRequest().setProduct(product);
//
//        ProductAddUpdateResponse productAddUpdateResponse = putRequest(
//                Endpoint.product,
//                MAPPER.writeValueAsString(productAddUpdateRequest),
//                ProductAddUpdateResponse.class
//        );
//
//        assertEquals(product.getName(), productAddUpdateResponse.getProduct().getName());
//
//
//    }

//    @Test
//    @SneakyThrows
//    public void productDeleteTest() {
//        ProductType productType = new ProductType().setType("TEST_TYPE");
//        ProductTypeAddRequest productTypeAddRequest = new ProductTypeAddRequest().setProductType(productType);
//
//        ProductTypeAddResponse productTypeAddResponse = putRequest(
//                Endpoint.productType,
//                MAPPER.writeValueAsString(productTypeAddRequest),
//                ProductTypeAddResponse.class
//        );
//
//        assertEquals(productType.getType(), productTypeAddResponse.getProductType().getType());
//
//        Product product = new Product()
//                .setName("TEST_PRODUCT")
//                .setProductType("TEST_TYPE")
//                .setExpirationDays(30);
//        ProductAddUpdateRequest productAddRequest = new ProductAddUpdateRequest().setProduct(product);
//
//        ProductAddUpdateResponse productAddResponse = putRequest(
//                Endpoint.product,
//                MAPPER.writeValueAsString(productAddRequest),
//                ProductAddUpdateResponse.class
//        );
//
//        assertEquals(product.getName(), productAddResponse.getProduct().getName());
//
//        ProductDeleteResponse productDeleteResponse = deleteRequest(
//                Endpoint.product,
//                "TEST_PRODUCT",
//                ProductDeleteResponse.class
//        );
//
//        assertEquals("TEST_PRODUCT", productDeleteResponse.getName());
//    }
}

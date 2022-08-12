package com.shopdashboardservice.endpoints;

import com.shopdashboardservice.AbstractRestTest;
import com.shopdashboardservice.model.ErrorResponse;
import com.shopdashboardservice.model.Product;
import com.shopdashboardservice.model.ProductToSell;
import com.shopdashboardservice.model.ProductType;
import com.shopdashboardservice.model.exception.AppException.ErrorCode;
import com.shopdashboardservice.model.listfilters.ProductToSellListFilter;
import com.shopdashboardservice.model.requests.product.ProductAddUpdateRequest;
import com.shopdashboardservice.model.requests.producttosell.ProductToSellAddUpdateRequest;
import com.shopdashboardservice.model.requests.producttosell.ProductToSellListRequest;
import com.shopdashboardservice.model.requests.producttype.ProductTypeAddRequest;
import com.shopdashboardservice.model.responses.product.ProductAddUpdateResponse;
import com.shopdashboardservice.model.responses.producttosell.ProductToSellAddUpdateResponse;
import com.shopdashboardservice.model.responses.producttosell.ProductToSellDeleteResponse;
import com.shopdashboardservice.model.responses.producttosell.ProductToSellGetResponse;
import com.shopdashboardservice.model.responses.producttosell.ProductToSellListResponse;
import com.shopdashboardservice.model.responses.producttype.ProductTypeAddResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Transactional("transactionManager")
public class ProductToSellEndpointsTest extends AbstractRestTest {

    @Test
    @SneakyThrows
    public void orderToOrderListTest() {
        ProductToSellListFilter filter = new ProductToSellListFilter();
        ProductToSellListRequest request = new ProductToSellListRequest().setFilter(filter);

        ProductToSellListResponse response = getListRequest(
                Endpoint.productToSellList,
                MAPPER.writeValueAsString(request),
                ProductToSellListResponse.class
        );

        assertEquals(1, response.getProducts().size());
    }

    @Test
    public void orderToOrderListErrorTest() {
        ErrorResponse response = getRequest(
                Endpoint.productToSellList,
                ErrorResponse.class
        );

        assertEquals(ErrorCode.TECHNICAL_ERROR, response.getErrorCode());
    }

    @Test
    public void productToSellGetTest() {
        ProductToSellGetResponse response = getRequest(
                Endpoint.productToSell,
                "Test Product Name",
                ProductToSellGetResponse.class
        );

        assertEquals("Test Product Name", response.getProduct().getProductName());
    }

    @Test
    public void productToSellGetErrorTest() {
        ProductToSellGetResponse response = getRequest(
                Endpoint.productToSell,
                "INVALID_PRODUCT_NAME",
                ProductToSellGetResponse.class
        );

        assertNull(response.getProduct());
    }

    @Test
    @SneakyThrows
    public void productToSellAddTest() {
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

        ProductToSell productToSell = new ProductToSell()
                .setProductName("TEST_PRODUCT")
                .setPrice(10.5);
        ProductToSellAddUpdateRequest productToSellAddRequest = new ProductToSellAddUpdateRequest().setProduct(productToSell);

        ProductToSellAddUpdateResponse productToSellAddResponse = putRequest(
                Endpoint.productToSell,
                MAPPER.writeValueAsString(productToSellAddRequest),
                ProductToSellAddUpdateResponse.class
        );
        assertEquals("TEST_PRODUCT", productToSellAddResponse.getProduct().getProductName());
    }

    @Test
    @SneakyThrows
    public void productToSellUpdateTest() {
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

        ProductToSell productToSell = new ProductToSell()
                .setProductName("TEST_PRODUCT")
                .setPrice(10.5);
        ProductToSellAddUpdateRequest productToSellAddRequest = new ProductToSellAddUpdateRequest().setProduct(productToSell);

        ProductToSellAddUpdateResponse productToSellAddResponse = putRequest(
                Endpoint.productToSell,
                MAPPER.writeValueAsString(productToSellAddRequest),
                ProductToSellAddUpdateResponse.class
        );
        assertEquals("TEST_PRODUCT", productToSellAddResponse.getProduct().getProductName());

        productToSell.setPrice(10.55);

        ProductToSellAddUpdateResponse productToSellUpdateResponse = postRequest(
                Endpoint.productToSell,
                MAPPER.writeValueAsString(productToSellAddRequest),
                ProductToSellAddUpdateResponse.class
        );
        assertEquals(10.55, productToSellUpdateResponse.getProduct().getPrice());
    }

    @Test
    @SneakyThrows
    public void productToSellDeleteTest() {
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

        ProductToSell productToSell = new ProductToSell()
                .setProductName("TEST_PRODUCT")
                .setPrice(10.5);
        ProductToSellAddUpdateRequest productToSellAddRequest = new ProductToSellAddUpdateRequest().setProduct(productToSell);

        ProductToSellAddUpdateResponse productToSellAddResponse = putRequest(
                Endpoint.productToSell,
                MAPPER.writeValueAsString(productToSellAddRequest),
                ProductToSellAddUpdateResponse.class
        );
        assertEquals("TEST_PRODUCT", productToSellAddResponse.getProduct().getProductName());

        ProductToSellDeleteResponse productToSellDeleteResponse = deleteRequest(
                Endpoint.productToSell,
                "TEST_PRODUCT",
                ProductToSellDeleteResponse.class
        );

        assertEquals("TEST_PRODUCT", productToSellDeleteResponse.getProductName());
    }
}

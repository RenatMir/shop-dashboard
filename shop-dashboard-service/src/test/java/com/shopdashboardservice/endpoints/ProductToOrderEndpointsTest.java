package com.shopdashboardservice.endpoints;

import com.shopdashboardservice.AbstractRestTest;
import com.shopdashboardservice.model.ErrorResponse;
import com.shopdashboardservice.model.Product;
import com.shopdashboardservice.model.ProductToOrder;
import com.shopdashboardservice.model.ProductType;
import com.shopdashboardservice.model.exception.AppException.ErrorCode;
import com.shopdashboardservice.model.listfilters.ProductToOrderListFilter;
import com.shopdashboardservice.model.requests.product.ProductAddUpdateRequest;
import com.shopdashboardservice.model.requests.producttoorder.ProductToOrderAddUpdateRequest;
import com.shopdashboardservice.model.requests.producttoorder.ProductToOrderListRequest;
import com.shopdashboardservice.model.requests.producttype.ProductTypeAddRequest;
import com.shopdashboardservice.model.responses.product.ProductAddUpdateResponse;
import com.shopdashboardservice.model.responses.producttoorder.ProductToOrderAddUpdateResponse;
import com.shopdashboardservice.model.responses.producttoorder.ProductToOrderDeleteResponse;
import com.shopdashboardservice.model.responses.producttoorder.ProductToOrderGetResponse;
import com.shopdashboardservice.model.responses.producttoorder.ProductToOrderListResponse;
import com.shopdashboardservice.model.responses.producttype.ProductTypeAddResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Transactional("transactionManager")
public class ProductToOrderEndpointsTest extends AbstractRestTest {

    @Test
    @SneakyThrows
    public void orderToOrderListTest() {
        ProductToOrderListFilter filter = new ProductToOrderListFilter();
        ProductToOrderListRequest request = new ProductToOrderListRequest().setFilter(filter);

        ProductToOrderListResponse response = getListRequest(
                Endpoint.productToOrderList,
                MAPPER.writeValueAsString(request),
                ProductToOrderListResponse.class
        );

        assertEquals(1, response.getProducts().size());
    }

    @Test
    public void orderToOrderListErrorTest() {
        ErrorResponse response = getRequest(
                Endpoint.productToOrderList,
                ErrorResponse.class
        );

        assertEquals(ErrorCode.TECHNICAL_ERROR, response.getErrorCode());
    }

    @Test
    public void productToOrderGetTest() {
        ProductToOrderGetResponse response = getRequest(
                Endpoint.productToOrder,
                "Test Product Name",
                ProductToOrderGetResponse.class
        );

        assertEquals("Test Product Name", response.getProduct().getProductName());
    }

    @Test
    public void productToOrderGetErrorTest() {
        ProductToOrderGetResponse response = getRequest(
                Endpoint.productToOrder,
                "INVALID_PRODUCT_NAME",
                ProductToOrderGetResponse.class
        );

        assertNull(response.getProduct());
    }

    @Test
    @SneakyThrows
    public void productToOrderAddTest() {
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

        ProductToOrder productToOrder = new ProductToOrder()
                .setProductName("TEST_PRODUCT")
                .setPrice(10.5);
        ProductToOrderAddUpdateRequest productToOrderAddRequest = new ProductToOrderAddUpdateRequest().setProduct(productToOrder);

        ProductToOrderAddUpdateResponse productToOrderAddResponse = putRequest(
                Endpoint.productToOrder,
                MAPPER.writeValueAsString(productToOrderAddRequest),
                ProductToOrderAddUpdateResponse.class
        );
        assertEquals("TEST_PRODUCT", productToOrderAddResponse.getProduct().getProductName());
    }

    @Test
    @SneakyThrows
    public void productToOrderUpdateTest() {
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

        ProductToOrder productToOrder = new ProductToOrder()
                .setProductName("TEST_PRODUCT")
                .setPrice(10.5);
        ProductToOrderAddUpdateRequest productToOrderAddRequest = new ProductToOrderAddUpdateRequest().setProduct(productToOrder);

        ProductToOrderAddUpdateResponse productToOrderAddResponse = putRequest(
                Endpoint.productToOrder,
                MAPPER.writeValueAsString(productToOrderAddRequest),
                ProductToOrderAddUpdateResponse.class
        );
        assertEquals("TEST_PRODUCT", productToOrderAddResponse.getProduct().getProductName());

        productToOrder.setPrice(10.55);

        ProductToOrderAddUpdateResponse productToOrderUpdateResponse = postRequest(
                Endpoint.productToOrder,
                MAPPER.writeValueAsString(productToOrderAddRequest),
                ProductToOrderAddUpdateResponse.class
        );
        assertEquals(10.55, productToOrderUpdateResponse.getProduct().getPrice());
    }

    @Test
    @SneakyThrows
    public void productToOrderDeleteTest() {
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

        ProductToOrder productToOrder = new ProductToOrder()
                .setProductName("TEST_PRODUCT")
                .setPrice(10.5);
        ProductToOrderAddUpdateRequest productToOrderAddRequest = new ProductToOrderAddUpdateRequest().setProduct(productToOrder);

        ProductToOrderAddUpdateResponse productToOrderAddResponse = putRequest(
                Endpoint.productToOrder,
                MAPPER.writeValueAsString(productToOrderAddRequest),
                ProductToOrderAddUpdateResponse.class
        );
        assertEquals("TEST_PRODUCT", productToOrderAddResponse.getProduct().getProductName());

        ProductToOrderDeleteResponse productToOrderDeleteResponse = deleteRequest(
                Endpoint.productToOrder,
                "TEST_PRODUCT",
                ProductToOrderDeleteResponse.class
        );

        assertEquals("TEST_PRODUCT", productToOrderDeleteResponse.getProductName());
    }
}

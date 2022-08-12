package com.shopdashboardservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Collections;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AbstractRestTest {

    public static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int serverPort;
    @ClassRule
    public static final TestEnvironment environment = TestEnvironment.getInstance();

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> environment.getPostgresUrl("postgres"));
    }

    protected <T> T getRequest(Endpoint endpoint, Class<T> responseClass) {
        return getRequest(endpoint, null, Collections.emptyMap(), responseClass);
    }

    protected <T> T getRequest(Endpoint endpoint, Object id, Class<T> responseClass) {
        return getRequest(endpoint, id, Collections.emptyMap(), responseClass);
    }

    @SneakyThrows
    protected <T> T getRequest(Endpoint endpoint, Object id,  Map<String, String> params, Class<T> responseClass) {
        String idUriSuffix = id != null ? String.format("/%s", id) : "";
        String url = String.format("http://localhost:%d/%s%s", serverPort, endpoint.getPath(), idUriSuffix);
        MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.get(url);

        if(params != null){
            for (Map.Entry<String, String> param : params.entrySet()) {
                getRequest.param(param.getKey(), param.getValue());
            }
        }

        MvcResult response = mockMvc.perform(getRequest).andReturn();
        return MAPPER.readValue(response.getResponse().getContentAsString(), responseClass);
    }

    @SneakyThrows
    protected <T> T getListRequest(Endpoint endpoint, String payload, Class<T> responseClass) {
        String url = String.format("http://localhost:%d/%s", serverPort, endpoint.getPath());
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.get(url);
        postRequest.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(payload);

        MvcResult response = mockMvc.perform(postRequest).andReturn();
        return MAPPER.readValue(response.getResponse().getContentAsString(), responseClass);
    }

    @SneakyThrows
    protected <T> T postRequest(Endpoint endpoint, String payload, Class<T> responseClass) {
        String url = String.format("http://localhost:%d/%s", serverPort, endpoint.getPath());
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post(url);
        postRequest.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(payload);

        MvcResult response = mockMvc.perform(postRequest).andReturn();
        return MAPPER.readValue(response.getResponse().getContentAsString(), responseClass);
    }

    @SneakyThrows
    protected <T> T putRequest(Endpoint endpoint, String payload, Class<T> responseClass) {
        String url = String.format("http://localhost:%d/%s", serverPort, endpoint.getPath());
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.put(url);
        postRequest.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(payload);

        MvcResult response = mockMvc.perform(postRequest).andReturn();
        return MAPPER.readValue(response.getResponse().getContentAsString(), responseClass);
    }

    @SneakyThrows
    protected <T> T deleteRequest(Endpoint endpoint, Object id, Class<T> responseClass) {
        String idUriSuffix = id != null ? String.format("/%s", id) : "";
        String url = String.format("http://localhost:%d/%s%s", serverPort, endpoint.getPath(), idUriSuffix);
        MockHttpServletRequestBuilder deleteRequest = MockMvcRequestBuilders.delete(url);
        deleteRequest.contentType(org.springframework.http.MediaType.APPLICATION_JSON);

        MvcResult response = mockMvc.perform(deleteRequest).andReturn();
        return MAPPER.readValue(response.getResponse().getContentAsString(), responseClass);
    }

    @Getter
    @AllArgsConstructor
    public enum Endpoint {
        ping("ping"),
        productList("product/list"),
        product("product"),
        productTypeList("product-type/list"),
        productType("product-type"),
        orderList("order/list"),
        order("order"),
        productToSellList("sell-product/list"),
        productToSell("sell-product"),
        productToOrderList("order-product/list"),
        productToOrder("order-product");

        private String path;
    }

}

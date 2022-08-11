package com.shopdashboardservice.controller;

import com.shopdashboardservice.model.Product;
import com.shopdashboardservice.model.ProductToOrder;
import com.shopdashboardservice.model.requests.product.ProductAddUpdateRequest;
import com.shopdashboardservice.model.requests.product.ProductGetDeleteRequest;
import com.shopdashboardservice.model.requests.producttoorder.ProductToOrderAddUpdateRequest;
import com.shopdashboardservice.model.requests.producttoorder.ProductToOrderGetDeleteRequest;
import com.shopdashboardservice.model.requests.producttoorder.ProductToOrderListRequest;
import com.shopdashboardservice.model.responses.product.ProductAddUpdateResponse;
import com.shopdashboardservice.model.responses.product.ProductDeleteResponse;
import com.shopdashboardservice.model.responses.product.ProductGetResponse;
import com.shopdashboardservice.model.responses.producttoorder.ProductToOrderAddUpdateResponse;
import com.shopdashboardservice.model.responses.producttoorder.ProductToOrderDeleteResponse;
import com.shopdashboardservice.model.responses.producttoorder.ProductToOrderGetResponse;
import com.shopdashboardservice.model.responses.producttoorder.ProductToOrderListResponse;
import com.shopdashboardservice.service.ProductToOrderService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("order-product")
public class ProductToOrderController {

    private final ProductToOrderService productToOrderService;

    @GetMapping("/list")
    public ResponseEntity<ProductToOrderListResponse> getProductsToOrderByFilter(@RequestBody @Valid ProductToOrderListRequest request) {
        List<ProductToOrder> products = productToOrderService.getProductsToOrderByFilter(request.getFilter());
        Integer totalRowCount = productToOrderService.getTotalRowCount(request.getFilter());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductToOrderListResponse()
                        .setFilter(request.getFilter())
                        .setProducts(products)
                        .setTotalRowCount(totalRowCount));
    }

    @GetMapping("/{productName}")
    public ResponseEntity<ProductToOrderGetResponse> getProductToOrderByProductName(@Valid final ProductToOrderGetDeleteRequest request){
        ProductToOrder product = productToOrderService.getProductToOrderByProductName(request.getProductName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductToOrderGetResponse()
                        .setProduct(product));
    }

    @PutMapping
    public ResponseEntity<ProductToOrderAddUpdateResponse> addProductToOrder(@RequestBody @Valid ProductToOrderAddUpdateRequest request){
        ProductToOrder addProduct = productToOrderService.addProductToOrder(request.getProduct());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductToOrderAddUpdateResponse()
                        .setProduct(addProduct));
    }

    @PostMapping
    public ResponseEntity<ProductToOrderAddUpdateResponse> updateProductToOrder(@RequestBody @Valid ProductToOrderAddUpdateRequest request){
        ProductToOrder updateProduct = productToOrderService.updateProductToOrder(request.getProduct());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductToOrderAddUpdateResponse()
                        .setProduct(updateProduct));
    }

    @DeleteMapping("/{productName}")
    public ResponseEntity<ProductToOrderDeleteResponse> deleteProductToOrder(@Valid ProductToOrderGetDeleteRequest request){
        productToOrderService.deleteProductToOrder(request.getProductName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductToOrderDeleteResponse()
                        .setProductName(request.getProductName()));
    }
}

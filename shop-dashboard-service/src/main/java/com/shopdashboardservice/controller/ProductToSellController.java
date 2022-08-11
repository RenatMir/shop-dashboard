package com.shopdashboardservice.controller;

import com.shopdashboardservice.model.ProductToSell;
import com.shopdashboardservice.model.requests.producttosell.ProductToSellAddUpdateRequest;
import com.shopdashboardservice.model.requests.producttosell.ProductToSellGetDeleteRequest;
import com.shopdashboardservice.model.requests.producttosell.ProductToSellListRequest;
import com.shopdashboardservice.model.responses.producttosell.ProductToSellAddUpdateResponse;
import com.shopdashboardservice.model.responses.producttosell.ProductToSellDeleteResponse;
import com.shopdashboardservice.model.responses.producttosell.ProductToSellGetResponse;
import com.shopdashboardservice.model.responses.producttosell.ProductToSellListResponse;
import com.shopdashboardservice.service.ProductToSellService;
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
@RequestMapping("sell-product")
public class ProductToSellController {

    private final ProductToSellService productToSellService;

    @GetMapping("/list")
    public ResponseEntity<ProductToSellListResponse> getProductsToSellByFilter(@RequestBody @Valid ProductToSellListRequest request) {
        List<ProductToSell> products = productToSellService.getProductsToSellByFilter(request.getFilter());
        Integer totalRowCount = productToSellService.getTotalRowCount(request.getFilter());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductToSellListResponse()
                        .setFilter(request.getFilter())
                        .setProducts(products)
                        .setTotalRowCount(totalRowCount));
    }

    @GetMapping("/{productName}")
    public ResponseEntity<ProductToSellGetResponse> getProductToSellByProductName(@Valid final ProductToSellGetDeleteRequest request){
        ProductToSell product = productToSellService.getProductToSellByProductName(request.getProductName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductToSellGetResponse()
                        .setProduct(product));
    }

    @PutMapping
    public ResponseEntity<ProductToSellAddUpdateResponse> addProductToSell(@RequestBody @Valid ProductToSellAddUpdateRequest request){
        ProductToSell addProduct = productToSellService.addProductToSell(request.getProduct());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductToSellAddUpdateResponse()
                        .setProduct(addProduct));
    }

    @PostMapping
    public ResponseEntity<ProductToSellAddUpdateResponse> updateProductToSell(@RequestBody @Valid ProductToSellAddUpdateRequest request){
        ProductToSell updateProduct = productToSellService.updateProductToSell(request.getProduct());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductToSellAddUpdateResponse()
                        .setProduct(updateProduct));
    }

    @DeleteMapping("/{productName}")
    public ResponseEntity<ProductToSellDeleteResponse> deleteProductToSell(@Valid ProductToSellGetDeleteRequest request){
        productToSellService.deleteProductToSell(request.getProductName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductToSellDeleteResponse()
                        .setProductName(request.getProductName()));
    }
}

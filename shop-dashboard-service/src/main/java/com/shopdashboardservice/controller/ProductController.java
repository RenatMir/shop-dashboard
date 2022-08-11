package com.shopdashboardservice.controller;

import com.shopdashboardservice.model.Product;
import com.shopdashboardservice.model.requests.product.ProductAddUpdateRequest;
import com.shopdashboardservice.model.requests.product.ProductGetDeleteRequest;
import com.shopdashboardservice.model.requests.product.ProductListRequest;
import com.shopdashboardservice.model.responses.product.ProductAddUpdateResponse;
import com.shopdashboardservice.model.responses.product.ProductDeleteResponse;
import com.shopdashboardservice.model.responses.product.ProductGetResponse;
import com.shopdashboardservice.model.responses.product.ProductListResponse;
import com.shopdashboardservice.service.ProductService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<ProductListResponse> getProductsByFilter(@RequestBody @Valid ProductListRequest request) {
        List<Product> products = productService.getProductsByFilter(request.getFilter());
        Integer totalRowCount = productService.getTotalRowCount(request.getFilter());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductListResponse()
                        .setFilter(request.getFilter())
                        .setProducts(products)
                        .setTotalRowCount(totalRowCount));
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductGetResponse> getProductByName(@Valid final ProductGetDeleteRequest request){
        Product product =  productService.getProductByName(request.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductGetResponse()
                        .setProduct(product));
    }

    @PutMapping
    public ResponseEntity<ProductAddUpdateResponse> addProductType(@RequestBody @Valid ProductAddUpdateRequest request){
        Product addProduct = productService.addProduct(request.getProduct());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductAddUpdateResponse()
                        .setProduct(addProduct));
    }

//    @PostMapping
//    public ResponseEntity<ProductAddUpdateResponse> updateProductType(@RequestBody @Valid ProductAddUpdateRequest request){
//        Product updateProduct = productService.updateProduct(request.getProduct());
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(new ProductAddUpdateResponse()
//                        .setProduct(updateProduct));
//    }

//    @DeleteMapping("/{name}")
//    public ResponseEntity<ProductDeleteResponse> deleteProductType(@Valid ProductGetDeleteRequest request){
//        productService.deleteProduct(request.getName());
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(new ProductDeleteResponse()
//                        .setName(request.getName()));
//    }
}

package com.shopdashboardservice.controller;

import com.shopdashboardservice.model.ProductType;
import com.shopdashboardservice.model.requests.producttype.ProductTypeAddRequest;
import com.shopdashboardservice.model.requests.producttype.ProductTypeGetDeleteRequest;
import com.shopdashboardservice.model.requests.producttype.ProductTypeListRequest;
import com.shopdashboardservice.model.responses.producttype.ProductTypeAddResponse;
import com.shopdashboardservice.model.responses.producttype.ProductTypeDeleteResponse;
import com.shopdashboardservice.model.responses.producttype.ProductTypeGetResponse;
import com.shopdashboardservice.model.responses.producttype.ProductTypeListResponse;
import com.shopdashboardservice.service.ProductTypeService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/product-type")
public class ProductTypeController {

    public final ProductTypeService productTypeService;

    @GetMapping("/list")
    public ResponseEntity<ProductTypeListResponse> getProductTypesByFilter(@RequestBody @Valid ProductTypeListRequest request){
        List<ProductType> productTypes = productTypeService.getProductTypesByFilter(request.getFilter());
        Integer totalRowCount = productTypeService.getTotalRowCount(request.getFilter());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductTypeListResponse()
                        .setProductTypes(productTypes)
                        .setFilter(request.getFilter())
                        .setTotalRowCount(totalRowCount));
    }

    @GetMapping("/{type}")
    public ResponseEntity<ProductTypeGetResponse> getProductTypeByType(@Valid final ProductTypeGetDeleteRequest request){
        ProductType productType =  productTypeService.getProductTypeById(request.getType());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductTypeGetResponse()
                        .setProductType(productType));
    }

    @PutMapping
    public ResponseEntity<ProductTypeAddResponse> addProductType(@RequestBody @Valid ProductTypeAddRequest request){
        ProductType addProductType = productTypeService.addProductType(request.getProductType());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductTypeAddResponse()
                        .setProductType(addProductType));
    }

    @DeleteMapping("/{type}")
    public ResponseEntity<ProductTypeDeleteResponse> deleteProductType(@Valid ProductTypeGetDeleteRequest request){
        productTypeService.deleteProductType(request.getType());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductTypeDeleteResponse()
                        .setType(request.getType()));
    }
}

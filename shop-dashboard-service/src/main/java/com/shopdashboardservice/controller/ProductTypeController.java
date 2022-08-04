package com.shopdashboardservice.controller;

import com.shopdashboardservice.model.ProductType;
import com.shopdashboardservice.model.requests.producttype.ProductTypeAddUpdateRequest;
import com.shopdashboardservice.model.requests.producttype.ProductTypeGetDeleteRequest;
import com.shopdashboardservice.model.requests.producttype.ProductTypeListRequest;
import com.shopdashboardservice.model.responses.producttype.ProductTypeAddUpdateResponse;
import com.shopdashboardservice.model.responses.producttype.ProductTypeDeleteResponse;
import com.shopdashboardservice.model.responses.producttype.ProductTypeGetResponse;
import com.shopdashboardservice.model.responses.producttype.ProductTypeListResponse;
import com.shopdashboardservice.service.ProductTypeService;
import java.util.List;
import javax.validation.Valid;
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
@RequestMapping("/product-type")
public class ProductTypeController {

    public final ProductTypeService productTypeService;

    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @GetMapping("/list")
    public ResponseEntity<ProductTypeListResponse> getProductTypesByFilter(@RequestBody @Valid ProductTypeListRequest request){
        List<ProductType> productTypes = productTypeService.getProductTypesByFilter(request.getFilter());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductTypeListResponse()
                        .setProductTypes(productTypes)
                        .setFilter(request.getFilter())
                        //TODO ADD TOTAL ROW COUNT
                        .setTotalRowCount(1));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductTypeGetResponse> getProductTypeByTypeName(@Valid final ProductTypeGetDeleteRequest request){
        ProductType productType =  productTypeService.getProductTypeByTypeName(request.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductTypeGetResponse()
                        .setProductType(productType));
    }

    @PutMapping
    public ResponseEntity<ProductTypeAddUpdateResponse> addProductType(@RequestBody @Valid ProductTypeAddUpdateRequest request){
        ProductType addProductType = productTypeService.addProductType(request.getProductType());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductTypeAddUpdateResponse()
                        .setProductType(addProductType));
    }

    @PostMapping
    public ResponseEntity<ProductTypeAddUpdateResponse> updateProductType(@RequestBody @Valid ProductTypeAddUpdateRequest request){
        ProductType updateProductType = productTypeService.updateProductType(request.getProductType());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductTypeAddUpdateResponse()
                        .setProductType(updateProductType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductTypeDeleteResponse> deleteProductType(@Valid ProductTypeGetDeleteRequest request){
        productTypeService.deleteProductType(request.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ProductTypeDeleteResponse()
                        .setId(request.getId()));
    }
}

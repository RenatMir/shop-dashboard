package com.shopdashboardservice.service;

import com.shopdashboardservice.model.ProductType;
import com.shopdashboardservice.model.ProductTypesListFilter;
import com.shopdashboardservice.repository.ProductTypeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeService {

    public final ProductTypeRepository productTypeRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public List<ProductType> getProductTypesByFilter(ProductTypesListFilter filter){
        return productTypeRepository.getProductTypesByFilter(filter);
    }

    public ProductType getProductTypeByTypeName(Long id) {
        return productTypeRepository.getProductTypeByTypeName(id);
    }

    public ProductType addProductType(ProductType productType) {
        return productTypeRepository.addProductType(productType);
    }

    public ProductType updateProductType(ProductType productType) {
        return productTypeRepository.updateProductType(productType);
    }

    public void deleteProductType(Long id) {
        productTypeRepository.deleteProductType(id);
    }
}

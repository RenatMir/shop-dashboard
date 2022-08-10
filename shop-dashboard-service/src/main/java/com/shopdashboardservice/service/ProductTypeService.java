package com.shopdashboardservice.service;

import com.shopdashboardservice.model.ProductType;
import com.shopdashboardservice.model.listfilters.ProductTypeListFilter;
import com.shopdashboardservice.repository.ProductTypeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "transactionManager")
public class ProductTypeService {

    public final ProductTypeRepository productTypeRepository;

    public List<ProductType> getProductTypesByFilter(ProductTypeListFilter filter){
        return productTypeRepository.getProductTypesByFilter(filter);
    }

    public Integer getTotalRowCount(ProductTypeListFilter filter) {
        return productTypeRepository.getTotalRowCount(filter);
    }

    public ProductType getProductTypeById(String type) {
        return productTypeRepository.getProductTypeById(type);
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager")
    public ProductType addProductType(ProductType productType) {
        return productTypeRepository.addProductType(productType);
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager")
    public void deleteProductType(String type) {
        productTypeRepository.deleteProductType(type);
    }
}

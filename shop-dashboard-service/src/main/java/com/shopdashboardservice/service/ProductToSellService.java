package com.shopdashboardservice.service;

import com.shopdashboardservice.model.ProductToSell;
import com.shopdashboardservice.model.listfilters.ProductToSellListFilter;
import com.shopdashboardservice.repository.ProductToSellRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "transactionManager")
public class ProductToSellService {

    private final ProductToSellRepository productToSellRepository;

    public List<ProductToSell> getProductsToSellByFilter(ProductToSellListFilter filter) {
        return productToSellRepository.getProductsToSellByFilter(filter);
    }

    public Integer getTotalRowCount(ProductToSellListFilter filter) {
        return productToSellRepository.getTotalRowCount(filter);
    }

    public ProductToSell getProductToSellByProductName(String productName) {
        return productToSellRepository.getProductToSellByProductName(productName);
    }


    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager")
    public ProductToSell addProductToSell(ProductToSell product) {
        return productToSellRepository.addProductToSell(product);
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager")
    public ProductToSell updateProductToSell(ProductToSell product) {
        return productToSellRepository.updateProductToSell(product);
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager")
    public void deleteProductToSell(String productName) {
        productToSellRepository.deleteProductToSell(productName);
    }
}

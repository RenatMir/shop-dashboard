package com.shopdashboardservice.service;

import com.shopdashboardservice.model.Product;
import com.shopdashboardservice.model.ProductToOrder;
import com.shopdashboardservice.model.listfilters.ProductListFilter;
import com.shopdashboardservice.model.listfilters.ProductToOrderListFilter;
import com.shopdashboardservice.repository.ProductToOrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "transactionManager")
public class ProductToOrderService {

    private final ProductToOrderRepository productToOrderRepository;

    public List<ProductToOrder> getProductsToOrderByFilter(ProductToOrderListFilter filter) {
        return productToOrderRepository.getProductsToOrderByFilter(filter);
    }

    public Integer getTotalRowCount(ProductToOrderListFilter filter) {
        return productToOrderRepository.getTotalRowCount(filter);
    }

    public ProductToOrder getProductToOrderByProductName(String productName) {
        return productToOrderRepository.getProductToOrderByProductName(productName);
    }


    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager")
    public ProductToOrder addProductToOrder(ProductToOrder product) {
        return productToOrderRepository.addProductToOrder(product);
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager")
    public ProductToOrder updateProductToOrder(ProductToOrder product) {
        return productToOrderRepository.updateProductToOrder(product);
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager")
    public void deleteProductToOrder(String productName) {
        productToOrderRepository.deleteProductToOrder(productName);
    }
}

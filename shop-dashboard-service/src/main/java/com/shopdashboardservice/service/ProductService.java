package com.shopdashboardservice.service;

import com.shopdashboardservice.model.Product;
import com.shopdashboardservice.model.listfilters.ProductListFilter;
import com.shopdashboardservice.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, value = "transactionManager")
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getProductsByFilter(ProductListFilter filter) {
        return productRepository.getProductsByFilter(filter);
    }

    public Integer getTotalRowCount(ProductListFilter filter) {
        return productRepository.getTotalRowCount(filter);
    }

    public Product getProductById(Long id) {
        return productRepository.getProductById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager")
    public Product addProduct(Product product) {
        return productRepository.addProduct(product);
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager")
    public Product updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager")
    public void deleteProduct(Long id) {
        productRepository.deleteProduct(id);
    }
}

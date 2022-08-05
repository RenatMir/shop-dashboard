package com.shopdashboardservice.service;

import com.shopdashboardservice.model.Product;
import com.shopdashboardservice.model.ProductType;
import com.shopdashboardservice.model.listfilters.ProductListFilter;
import com.shopdashboardservice.model.listfilters.ProductTypesListFilter;
import com.shopdashboardservice.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductsByFilter(ProductListFilter filter) {
        return productRepository.getProductsByFilter(filter);
    }

    public Integer getTotalRowCount(ProductListFilter filter) {
        return productRepository.getTotalRowCount(filter);
    }

    public Product getProductById(Long id) {
        return productRepository.getProductById(id);
    }

    public Product addProduct(Product product) {
        return productRepository.addProduct(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteProduct(id);
    }
}

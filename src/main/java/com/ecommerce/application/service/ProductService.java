package com.ecommerce.application.service;

import com.ecommerce.application.model.Category;
import com.ecommerce.application.model.Product;
import com.ecommerce.application.repository.CategoryRepository;
import com.ecommerce.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    
    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    public Optional<Product> getSingleProduct(Long id){
        productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product does not exist."));
        return productRepository.findById(id);
    }

    public Product createProduct(Product product){
        Category categoryExists = categoryRepository.findById(product.getCategory_id()).orElseThrow(() -> new IllegalStateException("Category does not exist"));
        product.setCategory(categoryExists);

        Set<Product> productListInCategory = categoryExists.getProduct_list();
        productListInCategory.add(product);

        Product productSave = productRepository.save(product);

        return productSave;
    }

    public void deleteProduct(Long id){
        productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product does not exist."));
        productRepository.deleteById(id);
    }

    public Product updateProduct(Product product, Long id){
        Product productExist = productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product does not exists"));
        productExist.setCategory_id(product.getCategory_id());
        productExist.setName(product.getName());
        productExist.setDescription(product.getDescription());
        productExist.setPrice(product.getPrice());
        productExist.setStocks(product.getStocks());
        return productRepository.save(productExist);
    }
}

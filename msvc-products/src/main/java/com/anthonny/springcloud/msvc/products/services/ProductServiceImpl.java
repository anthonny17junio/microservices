package com.anthonny.springcloud.msvc.products.services;

import com.anthonny.springcloud.msvc.products.entities.Product;
import com.anthonny.springcloud.msvc.products.repositories.ProductRepository;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Environment environment;

    private static final String PORT_PROPERTY = "local.server.port";

    public ProductServiceImpl(
            ProductRepository productRepository,
            Environment environment
    ) {
        this.productRepository = productRepository;
        this.environment = environment;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return ((List<Product>) productRepository.findAll()).stream()
                .peek(product -> {
                    String port = environment.getProperty(PORT_PROPERTY);
                    if (port != null) {
                        product.setPort(Integer.parseInt(port));
                    }
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        productOptional.ifPresent(product ->
                product.setPort(environment.getProperty(PORT_PROPERTY, Integer.class))
        );
        return productOptional;
    }
}

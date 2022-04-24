package com.example.javaproject.service;

import com.example.javaproject.dto.ProductDto;
import com.example.javaproject.exception.definition.*;
import com.example.javaproject.mapper.ProductMapper;
import com.example.javaproject.model.*;
import com.example.javaproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

    public ProductDto findProductById(Long id) throws ProductNotFound {
        Optional<Product> product =  productRepository.findById(id);
        if (product.isEmpty())
            throw new ProductNotFound("Product not found");
        return productMapper.mapToDto(product.get());
    }

    public Product findById(Long id) throws ProductNotFound {
        Optional<Product> product =  productRepository.findById(id);
        if (product.isEmpty())
            throw new ProductNotFound("Product not found");
        return product.get();
    }

    public void deleteProductById(Long id) throws ProductNotFound {
        Product product = findById(id);

        productRepository.delete(product);
    }

    public ProductDto addProduct(ProductDto productDto) {
        Product product = productMapper.mapToEntity(productDto);

        Product savedProduct = productRepository.save(product);
        return productMapper.mapToDto(savedProduct);
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List <ProductDto> productsDto = products.stream().map(product -> productMapper.mapToDto(product))
                .collect(Collectors.toList());
        return productsDto;
    }

    public ProductDto updateProduct(Product product, Long id) throws ProductNotFound {
        findProductById(id);
        product.setId(id);
        return productMapper.mapToDto(productRepository.save(product));
    }
}

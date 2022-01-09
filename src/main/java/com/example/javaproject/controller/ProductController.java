package com.example.javaproject.controller;

import com.example.javaproject.dto.ProductDto;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.mapper.ProductMapper;
import com.example.javaproject.mapper.UserMapper;
import com.example.javaproject.model.ProductType;
import com.example.javaproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @PostMapping("/add/{productType}")
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto, @PathVariable ProductType productType) {
        productDto.setProductType(productType);
        return ResponseEntity
                .ok()
                .body(productService.addProduct(productDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable Long id) throws ProductNotFound {
        return ResponseEntity
                .ok()
                .body(productService.updateProduct(productMapper.mapToEntity(productDto), id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductDto> delete(@PathVariable Long id) throws ProductNotFound {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable Long id) throws ProductNotFound {
        return ResponseEntity
                .ok()
                .body(productService.findProductById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> get() {
        return ResponseEntity
                .ok()
                .body(productService.getAllProducts());
    }

    @GetMapping("/get/{type}")
    public ResponseEntity<List<ProductDto>> getByProductType(@PathVariable ProductType type) {
        return ResponseEntity
                .ok()
                .body(productService.getProductsByType(type));
    }
}

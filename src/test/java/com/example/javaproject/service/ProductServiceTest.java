package com.example.javaproject.service;

import com.example.javaproject.dto.ProductDto;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.mapper.ProductMapper;
import com.example.javaproject.model.Product;
import com.example.javaproject.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private static final Long ID = 1L;
    private static final String NAME = "dummy";

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Create product - works")
    void create_works() {
        ProductDto productDto = getProductDto();
        Product product = getProduct();

        Product savedProduct = getProduct();
        savedProduct.setId(ID);

        ProductDto returnedProductDto = getProductDto();
        returnedProductDto.setId(ID);

        when(productMapper.mapToEntity(productDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(productMapper.mapToDto(savedProduct)).thenReturn(returnedProductDto);

        ProductDto result = productService.addProduct(productDto);

        assertNotNull(result);
        assertEquals(returnedProductDto.getName(), result.getName());
        assertEquals(returnedProductDto.getId(), result.getId());
    }

    @Test
    @DisplayName("Update product - works")
    void update_works() throws ProductNotFound {
        Product product = getProduct();

        Product productFind = getProduct();
        productFind.setId(ID);
        Optional<Product> optionalProduct = Optional.of(productFind);

        Product savedProduct = getProduct();
        savedProduct.setId(ID);

        ProductDto returnedProductDTO = getProductDto();
        returnedProductDTO.setId(ID);

        when(productRepository.findById(ID)).thenReturn(optionalProduct);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(productMapper.mapToDto(savedProduct)).thenReturn(returnedProductDTO);

        ProductDto result = productService.updateProduct(product, ID);

        assertNotNull(result);
        assertEquals(returnedProductDTO.getName(), result.getName());
        assertEquals(returnedProductDTO.getId(), result.getId());
    }

    @Test
    @DisplayName("update product - fails")
    void update_productNotFound_fails() {
        Product product = getProduct();

        when(productRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ProductNotFound.class, () -> productService.updateProduct(product, ID));
    }

    @Test
    @DisplayName("delete product- works")
    void delete_works() throws ProductNotFound {
        Product product = getProduct();
        product.setId(ID);
        Optional<Product> optionalProduct = Optional.of(product);

        when(productRepository.findById(ID)).thenReturn(optionalProduct);

        productService.deleteProductById(ID);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    @DisplayName("delete product - fails")
    void delete_fails()  {
        when(productRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ProductNotFound.class, () -> productService.deleteProductById(ID));
    }

    @Test
    @DisplayName("Get all products - works")
    void getAll_works() throws ProductNotFound {
        List<Product> products = List.of(getProduct());
        List<ProductDto> productsDto =  List.of(getProductDto());

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.mapToDto(products.get(0))).thenReturn(productsDto.get(0));

        List<ProductDto> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(productsDto.get(0).getName(), result.get(0).getName());
        assertEquals(productsDto.get(0).getId(), result.get(0).getId());
    }

    private ProductDto getProductDto() {
        return ProductDto.builder()
                .name(NAME)
                .build();
    }

    private Product getProduct() {
        Product product = new Product();
        product.setName(NAME);

        return product;
    }
}
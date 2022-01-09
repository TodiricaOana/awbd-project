package com.example.javaproject.controller;

import com.example.javaproject.dto.ProductDto;
import com.example.javaproject.mapper.ProductMapper;
import com.example.javaproject.model.ProductType;
import com.example.javaproject.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
    private static final Long ID = 1L;
    private static final ProductType PRODUCT_TYPE = ProductType.RING;
    private static final Double PRICE = 100d;
    private static final String NAME = "dummy";
    private static final Integer NR_PRODUCTS = 10;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Testing creating a product")
    void test_create() throws Exception {
        //Arrange
        ProductDto dto = getProductDto();
        ProductType productType = ProductType.RING;
        when(productService.addProduct(any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(post("/api/product/add/" + productType)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    @DisplayName("Testing update a product")
    void test_update() throws Exception {
        //Arrange
        ProductDto dto = getProductDto();
        Long id = 1L;
        when(productService.updateProduct(any(), any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(put("/api/product/update/" + id)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    @DisplayName("Testing delete a product")
    void test_delete() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/product/delete/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Testing get a product")
    void test_get() throws Exception {
        ProductDto dto = getProductDto();
        Long id = 1L;
        when(productService.findProductById(any())).thenReturn(dto);

        mockMvc.perform(get("/api/product/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.price", is(dto.getPrice())))
                .andExpect(jsonPath("$.numberOfProducts", is(dto.getNumberOfProducts())));
    }

    @Test
    @DisplayName("Testing get all products")
    void test_getAll() throws Exception {
        List<ProductDto> dto = List.of(getProductDto());
        when(productService.getAllProducts()).thenReturn(dto);

        mockMvc.perform(get("/api/product/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is(dto.get(0).getName())))
                .andExpect(jsonPath("$[0].price", is(dto.get(0).getPrice())))
                .andExpect(jsonPath("$[0].numberOfProducts", is(dto.get(0).getNumberOfProducts())));
    }

    @Test
    @DisplayName("Testing get all products by type")
    void test_getByType() throws Exception {
        List<ProductDto> dto = List.of(getProductDto());
        ProductType productType = ProductType.RING;
        when(productService.getProductsByType(productType)).thenReturn(dto);

        mockMvc.perform(get("/api/product/get/" + productType))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is(dto.get(0).getName())))
                .andExpect(jsonPath("$[0].price", is(dto.get(0).getPrice())))
                .andExpect(jsonPath("$[0].numberOfProducts", is(dto.get(0).getNumberOfProducts())));
    }

    private ProductDto getProductDto() {
        return ProductDto.builder()
                .name(NAME)
                .productType(PRODUCT_TYPE)
                .numberOfProducts(NR_PRODUCTS)
                .price(PRICE)
                .id(ID)
                .build();
    }

}
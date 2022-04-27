package com.example.javaproject.controller;

import com.example.javaproject.dto.ProductDto;
import com.example.javaproject.exception.definition.ProductNotFound;
import com.example.javaproject.mapper.ProductMapper;
import com.example.javaproject.model.Product;
import com.example.javaproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @PostMapping
    public String addProduct(@Valid @ModelAttribute("product") ProductDto product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "productForm";
        }

        productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/form/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model) throws ProductNotFound {
        model.addAttribute("product", productService.findProductById(id));
        return "updateProductForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, @Valid  @ModelAttribute("product") ProductDto product, BindingResult bindingResult) throws ProductNotFound {
        if (bindingResult.hasErrors()) {
            return "updateProductForm";
        }

        productService.updateProduct(productMapper.mapToEntity(product), id);
        return "redirect:/products";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) throws ProductNotFound {
        productService.deleteProductById(id);
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) throws ProductNotFound {
        model.addAttribute("product", productService.findProductById(id));
        return "productInfo";
    }

    @GetMapping
    public String get(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/form")
    public String getForm(Model model) {
        model.addAttribute("product", new Product());
        return "productForm";
    }

}

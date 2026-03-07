package com.rou00.shopcart.service.product;

import com.rou00.shopcart.model.dto.ProductDTO;
import com.rou00.shopcart.model.entity.Product;

import java.util.List;

public interface ProductService {

    ProductDTO addProduct (ProductDTO productDto);
    ProductDTO getProductById(Long id);
    void deleteProductById(Long id);
    ProductDTO updateProductById(ProductDTO productDto, Long id);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductByBrandAndName(String brand, String name);





}

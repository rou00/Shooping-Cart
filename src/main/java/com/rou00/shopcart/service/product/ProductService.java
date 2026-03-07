package com.rou00.shopcart.service.product;

import com.rou00.shopcart.model.dto.ProductDTO;
import com.rou00.shopcart.model.entity.Product;

import java.util.List;

public interface ProductService {

    ProductDTO addProduct (ProductDTO productDto);
    ProductDTO getProductById(Long id);
    void deleteProductById(Long id);
    ProductDTO updateProductById(ProductDTO productDto, Long id);

    List<ProductDTO> getAllProducts();
    List<ProductDTO> getProductsByCategory(String category);
    List<ProductDTO> getProductsByBrand(String brand);
    List<ProductDTO> getProductsByCategoryAndBrand(String category, String brand);
    List<ProductDTO> getProductsByName(String name);
    List<ProductDTO> getProductsByBrandAndName(String brand, String name);

    Long countProductByBrandAndName(String brand, String name);





}

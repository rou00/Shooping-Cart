package com.rou00.shopcart.service.product.Impl;

import com.rou00.shopcart.exceptions.ResourceNotFound;
import com.rou00.shopcart.model.dto.ProductDTO;
import com.rou00.shopcart.model.entity.Category;
import com.rou00.shopcart.model.entity.Product;
import com.rou00.shopcart.repository.ProductRepository;
import com.rou00.shopcart.service.product.ProductService;
import com.rou00.shopcart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    // using the annotaion RequiredArgsConstructor and declaring the repository final , lombol will automaticaly generate the constructor and spring boot will inject it
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDTO addProduct(ProductDTO productDto) {
        Category category = Optional.ofNullable(categoryRepository.findByName(productDto.getCategory().getName()))
                                            .orElseGet(() -> {
                                                                 Category newCategory = new Category(productDto.getCategory().getName());
                                                                 return categoryRepository.save(newCategory);
                                                                });
        productDto.setCategory(category);
        return mapToProductDTO(productRepository.save(mapToProduct(productDto,category)));
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return mapToProductDTO(productRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Product Not Found !")));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,()-> { throw new ResourceNotFound("Product Want to delete Not Found!");});
    }

    @Override
    public ProductDTO updateProductById(ProductDTO productDto, Long id) {
        Product productCheck = productRepository.findById(id).orElseThrow(() -> new ResourceNotFound("No Such Product to Update!"));
        Category category = categoryRepository.findByName(productDto.getCategory().getName());
        Product product = mapToProduct(productDto,category);
        return  mapToProductDTO(productRepository.save(product));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    // Mapping , it is prefered to do it in a seperate package
    private Product mapToProduct(ProductDTO productDto, Category category){
        return new Product(
                productDto.getName(),
                productDto.getBrand(),
                productDto.getPrice(),
                productDto.getInventory(),
                productDto.getDescription(),
                category
        );
    }
    private ProductDTO mapToProductDTO(Product product){
        return new ProductDTO(
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getInventory(),
                product.getDescription(),
                product.getCategory()
        );
    }
}

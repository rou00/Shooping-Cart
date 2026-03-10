package com.rou00.shopcart.service.product.Impl;

import com.rou00.shopcart.exceptions.ResourceNotFound;
import com.rou00.shopcart.model.dto.ImageDTO;
import com.rou00.shopcart.model.dto.ProductDTO;
import com.rou00.shopcart.model.entity.Category;
import com.rou00.shopcart.model.entity.Image;
import com.rou00.shopcart.model.entity.Product;
import com.rou00.shopcart.repository.ImageRepository;
import com.rou00.shopcart.repository.ProductRepository;
import com.rou00.shopcart.service.product.ProductService;
import com.rou00.shopcart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    // using the annotaion RequiredArgsConstructor and declaring the repository final , lombol will automaticaly generate the constructor and spring boot will inject it
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private final ImageRepository imageRepository;

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
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Product Not Found !"));
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
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return mapListFromProductToProductDto(products);
    }



    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryName(category);
        return mapListFromProductToProductDto(products);

    }

    @Override
    public List<ProductDTO> getProductsByBrand(String brand) {
        List<Product> products = productRepository.findByBrand(brand);
        return mapListFromProductToProductDto(products);
    }

    @Override
    public List<ProductDTO> getProductsByCategoryAndBrand(String category, String brand) {
        List<Product> products =productRepository.findByCategoryNameAndBrand(category,brand);
        return mapListFromProductToProductDto(products);
    }

    @Override
    public List<ProductDTO> getProductsByName(String name) {
        List<Product> products = productRepository.findByName(name);
        return mapListFromProductToProductDto(products);
    }

    @Override
    public List<ProductDTO> getProductsByBrandAndName(String brand, String name) {
        List<Product> products =  productRepository.findByBrandAndName(brand,name);
        return mapListFromProductToProductDto(products);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    // Mapping , it is prefered to do it in a seperate package
    public Product mapToProduct(ProductDTO productDto, Category category){
        return new Product(
                productDto.getName(),
                productDto.getBrand(),
                productDto.getPrice(),
                productDto.getInventory(),
                productDto.getDescription(),
                category
        );
    }
    public Product mapToProd(ProductDTO productDto){
        return new Product(
                productDto.getName(),
                productDto.getBrand(),
                productDto.getPrice(),
                productDto.getInventory(),
                productDto.getDescription(),
                productDto.getCategory()
        );
    }
    public ProductDTO mapToProductDTO(Product product){
        return new ProductDTO(
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getInventory(),
                product.getDescription(),
                product.getCategory()
        );
    }
    private List<ProductDTO> mapListFromProductToProductDto(List<Product> products){
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product p : products){
            productDTOS.add(mapToProductDTO(p));
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getConvertedProducts(List<Product> products){
        return products.stream().map(this::converttoDto).toList();
    }

    @Override
    public ProductDTO converttoDto(Product product){
        ProductDTO productDTO = modelMapper.map(product,ProductDTO.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDTO> imageDTOS = images.stream().map(image -> modelMapper.map(image, ImageDTO.class)).toList();
        productDTO.setImages(imageDTOS);
        return productDTO;
    }
}

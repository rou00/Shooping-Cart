package com.rou00.shopcart.controller;

import com.rou00.shopcart.model.dto.ProductDTO;
import com.rou00.shopcart.repository.ProductRepository;
import com.rou00.shopcart.service.product.Impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final ProductServiceImpl productService;
    private final ProductRepository productRepository;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> productDTOs = productService.getAllProducts();
        return new ResponseEntity<>(productDTOs, HttpStatus.FOUND);
    }

    @GetMapping("/product/getById/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId){
        try {
            ProductDTO productDTO = productService.getProductById(productId);
            return new ResponseEntity<>(productDTO,HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product/add")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO){
        try {
            ProductDTO theProduct = productService.addProduct(productDTO);
            return new ResponseEntity<>(theProduct,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/product/update/{productId}")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO,@PathVariable Long productId){
        try {
            ProductDTO theProduct = productService.updateProductById(productDTO,productId);
            return new ResponseEntity<>(theProduct,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/product/delete/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return new ResponseEntity<>(null,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        }
    }
    @GetMapping("/getByBrand&Name/{brandName}/{productName}")
    public ResponseEntity<List<ProductDTO>> getProductByBrandName(@PathVariable String brandName,@PathVariable String productName){
        try {
            List<ProductDTO> productDTOS = productService.getProductsByBrandAndName(brandName,productName);
            if(!productDTOS.isEmpty()){
                return new ResponseEntity<>(productDTOS,HttpStatus.FOUND);
            }
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getByCategory&Brand/{categoryName}/{brandName}")
    public ResponseEntity<List<ProductDTO>> getProductByCategoryName(@PathVariable String categoryName,@PathVariable String brandName){
        try {
            List<ProductDTO> productDTOS = productService.getProductsByCategoryAndBrand(categoryName,brandName);
            if(!productDTOS.isEmpty()){
                return new ResponseEntity<>(productDTOS,HttpStatus.FOUND);
            }
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getByProductName/{productName}")
    public ResponseEntity<List<ProductDTO>> getProductByProductName(@PathVariable String productName){
        try {
            List<ProductDTO> productDTOS = productService.getProductsByName(productName);
            if(!productDTOS.isEmpty()){
                return new ResponseEntity<>(productDTOS,HttpStatus.FOUND);
            }
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

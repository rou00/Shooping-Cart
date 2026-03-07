package com.rou00.shopcart.controller;

import com.rou00.shopcart.model.dto.CategoryDTO;
import com.rou00.shopcart.model.entity.Category;
import com.rou00.shopcart.repository.CategoryRepository;
import com.rou00.shopcart.service.category.Impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/all")
    public ResponseEntity< List<CategoryDTO>> getAllCategories(){
        try {
            List<Category> categories = categoryService.getAllCategories();
            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            for(Category c : categories){
                categoryDTOS.add(categoryService.mapTOCategoryDTO(c));
            }
            return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO name){
        try {
            CategoryDTO theCategory = categoryService.addCategory(name);
            return new ResponseEntity<>(theCategory,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.CONFLICT);

        }
    }

    @GetMapping("/category/getById/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long categoryId){
        try{
        CategoryDTO categoryDTO = categoryService.getCategortyById(categoryId);
        return new ResponseEntity<>(categoryDTO,HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        }
    }

    @GetMapping("/category/getByName/{categoryName}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String categoryName){
        try{
            CategoryDTO categoryDTO = categoryService.getCategoryByName(categoryName);
            return new ResponseEntity<>(categoryDTO,HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/category/delete/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
        try{
             categoryService.deleteCategoryById(categoryId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        }
    }

    @PutMapping("/category/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        try {
            CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO,id);
            return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
}

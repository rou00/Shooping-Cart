package com.rou00.shopcart.service.category;

import com.rou00.shopcart.model.dto.CategoryDTO;
import com.rou00.shopcart.model.entity.Category;

import java.util.List;

public interface CategoryService {

    CategoryDTO getCategortyById(Long id);
    CategoryDTO getCategoryByName(String name);
    List<Category> getAllCategories();
    CategoryDTO addCategory(CategoryDTO categoryDto);
    CategoryDTO updateCategory(CategoryDTO categoryDto, Long id);
    void deleteCategoryById(Long id);

}

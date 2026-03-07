package com.rou00.shopcart.service.category.Impl;

import com.rou00.shopcart.exceptions.ResourceExists;
import com.rou00.shopcart.exceptions.ResourceNotFound;
import com.rou00.shopcart.model.dto.CategoryDTO;
import com.rou00.shopcart.model.dto.ProductDTO;
import com.rou00.shopcart.model.entity.Category;
import com.rou00.shopcart.model.entity.Product;
import com.rou00.shopcart.repository.CategoryRepository;
import com.rou00.shopcart.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO getCategortyById(Long id) {
        return mapTOCategoryDTO( categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFound("No Such Category Found!")));
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        return mapTOCategoryDTO(categoryRepository.findByName(name));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDto) {
        return mapTOCategoryDTO(Optional.of(mapToCategory(categoryDto)).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save).orElseThrow(()-> new ResourceExists(categoryDto.getName() +" Category Already Exists!"))
        );
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDto, Long id) {
        return mapTOCategoryDTO(Optional.ofNullable(getCategortyById(id))
                .map(oldCategory ->{
                    oldCategory.setName(categoryDto.getName());
                    return categoryRepository.save(mapToCategory(oldCategory));
                } ).orElseThrow(()-> new ResourceNotFound("the Category you want to Update Not Found ")));


    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new ResourceNotFound("The Category you Want to Delete Not Found!");
                });
    }

    // Mapping , it is prefered to do it in a seperate package
    private Category mapToCategory(CategoryDTO categoryDto){
        return new Category(
                categoryDto.getName()
        );
    }
    private CategoryDTO mapTOCategoryDTO(Category category){
        return new CategoryDTO(
                category.getName()
        );
    }
}

package com.micosoft.taskappbackendmv.categories;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CategoryService {
    private final  CategoryRepository categoryRepository;
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Long id) {
        Optional<Category> categoryDb = categoryRepository.findById(id);
        if (categoryDb.isPresent()){
            return categoryDb.get();
        }else {
            throw new IllegalStateException("Category doe not exist");
        }
    }

    public Category createCategory( Category category) {
        Optional<Category> categoryDb = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
        if (categoryDb.isEmpty()){
            return categoryRepository.save(category);
        }else {
            throw new IllegalStateException("Category already exists");
        }

    }

    public String deleteCategory(Long id) {
        Optional<Category> categoryDb = categoryRepository.findById(id);
        if (categoryDb.isPresent()){
            categoryRepository.deleteById(id);
            return "Deleted successfully";
        }else {
            throw new IllegalStateException("Category does not exist");
        }
    }

    public Category updateCategory(Long id, Category category) {
        Optional<Category> categoryDb = categoryRepository.findById(id);
        if (categoryDb.isEmpty()){
            throw new IllegalStateException("Category not found");
        }else if (!category.getCategoryName().isEmpty()&&!category.getCategoryName().equals(categoryDb.get().getCategoryName())){
            categoryDb.get().setCategoryName(category.getCategoryName());
        }
        if (!category.getCategoryColor().isEmpty()&&!category.getCategoryColor().equals(categoryDb.get().getCategoryColor())){
            categoryDb.get().setCategoryColor(category.getCategoryColor());
        }
        return  categoryRepository.save(categoryDb.get());
    }
}

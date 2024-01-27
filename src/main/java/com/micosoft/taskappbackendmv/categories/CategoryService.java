package com.micosoft.taskappbackendmv.categories;

import com.micosoft.taskappbackendmv.errors.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class CategoryService {
    @Autowired
    private final  CategoryRepository categoryRepository;
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Long id) {
        Optional<Category> categoryDb = categoryRepository.findById(id);
        if (categoryDb.isPresent()){
            return categoryDb.get();
        }else {
            throw new NotFoundException("Category doe not exist");
        }
    }

    public Category createCategory( Category category) {
        Optional<Category> categoryDb = categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
        if (categoryDb.isEmpty()){
            return categoryRepository.save(category);
        }else {
            throw new NotFoundException("Category already exists");
        }

    }

    public String deleteCategory(Long id) {
        Optional<Category> categoryDb = categoryRepository.findById(id);
        if (categoryDb.isPresent()){
            categoryRepository.deleteById(id);
            return "Deleted successfully";
        }else {
            throw new NotFoundException("Category does not exist");
        }
    }

    public Category updateCategory(Long id, Category category) {
        Optional<Category> categoryDb = categoryRepository.findById(id);
        if (categoryDb.isEmpty()){
            throw new NotFoundException("Category not found");
        }else if (!category.getCategoryName().isEmpty()&&!category.getCategoryName().equals(categoryDb.get().getCategoryName())){
            categoryDb.get().setCategoryName(category.getCategoryName());
        }
        if (!category.getCategoryColor().isEmpty()&&!category.getCategoryColor().equals(categoryDb.get().getCategoryColor())){
            categoryDb.get().setCategoryColor(category.getCategoryColor());
        }
        return  categoryRepository.save(categoryDb.get());
    }
}

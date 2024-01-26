package com.micosoft.taskappbackendmv.categories;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    CategoryService categoryService;
    @GetMapping
    public List<Category> gettingAllCategory(){
        return categoryService.getAllCategories();
    }
    @GetMapping("{id}")
    public Category gettingSingleCategory(@PathVariable Long id){
        return categoryService.getCategory(id);
    }
    @PostMapping
    public  Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }
    @DeleteMapping("{id}")
    public String deleteCategory(@PathVariable Long id){
        return  categoryService.deleteCategory(id);
    }
    @PutMapping("{id}")
    public Category updateCategory(@PathVariable Long id,@RequestBody Category category){
        return categoryService.updateCategory(id, category);
    }

}

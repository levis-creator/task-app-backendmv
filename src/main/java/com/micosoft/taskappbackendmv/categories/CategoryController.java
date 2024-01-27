package com.micosoft.taskappbackendmv.categories;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private  final CategoryService categoryService;
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

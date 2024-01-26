package com.micosoft.taskappbackendmv.categories;

import com.micosoft.taskappbackendmv.errors.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryService categoryService;

    Category category;
    Long categoryId=1L;
    Category updateCategory = Category.builder().categoryId(1L).categoryName("Work stuff").categoryColor("#ffffe").build();

    @BeforeEach
    void setUp() {
        category = Category.builder().categoryId(1L).categoryName("Work").categoryColor("#ffff").build();
    }

    @Test
    void gettingAllCategories() {
        categoryService.getAllCategories();
        verify(categoryRepository).findAll();
    }
    @Test
    void gettingCategoryWhenDoesNotExist(){
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()-> categoryService.getCategory(categoryId));
    }
    @Test
    void  gettingCategoryWhenExists(){
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        assertNotNull(categoryService.getCategory(categoryId));
    }
    @Test
    void creatingCategoryWhenExist(){
        when(categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName())).thenReturn(Optional.of(category));
        assertThrows(NotFoundException.class, ()->categoryService.createCategory(category));
        verify(categoryRepository,never()).save(any());
    }
    @Test
    void creatingCategoryWhenDoesNotExist(){
        when(categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName())).thenReturn(Optional.empty());
        when(categoryRepository.save(category)).thenReturn(category);
        assertThat(categoryService.createCategory(category)).isNotNull();
        verify(categoryRepository).save(category);
    }
    @Test
    void deletingWhenCategoryExist(){
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        assertThat(categoryService.deleteCategory(categoryId)).isNotNull();
        verify(categoryRepository).deleteById(categoryId);
    }
    @Test
    void  deletingWhenCategoryDoesNotExist(){
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,()->categoryService.deleteCategory(categoryId));
        verify(categoryRepository,never()).deleteById(any());
    }
    @Test
    void updatingWhenCategoryDoesNotExist(){
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()->categoryService.updateCategory(categoryId, category));
        verify(categoryRepository, never()).save(any());
    }
    @Test
    void updatingWhenCategoryExists(){
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);
        Category result=categoryService.updateCategory(categoryId, updateCategory);
        assertAll(
                ()->assertEquals(updateCategory.getCategoryName(), result.getCategoryName()),
                ()->assertEquals(updateCategory.getCategoryColor(),result.getCategoryColor())
        );

    }



}
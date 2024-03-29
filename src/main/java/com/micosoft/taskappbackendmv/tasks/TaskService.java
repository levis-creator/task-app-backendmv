package com.micosoft.taskappbackendmv.tasks;

import com.micosoft.taskappbackendmv.categories.Category;
import com.micosoft.taskappbackendmv.categories.CategoryRepository;
import com.micosoft.taskappbackendmv.errors.NotFoundException;
import com.micosoft.taskappbackendmv.tags.Tags;
import com.micosoft.taskappbackendmv.tags.TagsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;
    @Autowired
    private final TagsRepository tagsRepository;
    @Autowired
    private final CategoryRepository categoryRepository;


    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Long id) {
        boolean taskExist = taskRepository.existsById(id);
        if (taskExist) {
            return taskRepository.findById(id).get();
        } else {
            throw new NotFoundException("task does not exist");
        }
    }

    @Transactional
    public ResponseEntity<?> createTask(Task task) {
        Category inputCategory = task.getCategory();
        Optional<Category> categoryDb = categoryRepository.findById(inputCategory.getCategoryId());
        if (categoryDb.isEmpty()) {
            throw new NotFoundException("Category not found");
        } else {
            Category category = categoryDb.get();
            task.setCategory(category); // Set the Category directly

            Task savedTask = taskRepository.save(task); // Save the Task

            category.getTasks().add(savedTask); // Add the Task to the Category
            categoryRepository.save(category); // Save the updated Category
            savedTask.getTags().addAll(task.getTags());
            taskRepository.save(savedTask);
            return ResponseEntity.ok(savedTask);
        }
    }

    public String deleteTask(Long id) {
        boolean taskExist = taskRepository.existsById(id);
        if (taskExist) {
            taskRepository.deleteById(id);
            return "Deleted successfully";
        } else {
            throw new NotFoundException("task does not exists");
        }
    }


    public Task updateTask(Long id, Task task) {
        Optional<Task> taskDb = taskRepository.findById(id);
//
//        if (taskDb.isEmpty()){
//            throw new NotFoundException("task does not exists");
//        }else if (!task.getTaskName().isEmpty()
//                &&!task.getTaskName().equals(taskDb.get().getTaskName())){
//            taskDb.get().setTaskName(task.getTaskName());
//        }
////        Description
//        if (!task.getTaskDescription().isEmpty()&&!task.getTaskDescription().equals(taskDb.get().getTaskDescription())){
//            taskDb.get().setTaskDescription(task.getTaskDescription());
//        }
////        tags
//        if (!task.getTags().isEmpty()&&!task.getTags().equals(taskDb.get().getTags())){
//            taskDb.get().setTags(task.getTags());
//        }
//////      category
////        if (!task.getCategory().isEmpty()
////                && !task.getCategory().equals(taskDb.get().getCategory())){
////            taskDb.get().setCategory(task.getCategory());
////        }
////       Due date
//        if (task.getDueDate() != null &&!task.getDueDate().equals(taskDb.get().getDueDate())){
//            taskDb.get().setDueDate(task.getDueDate());
//        }
//
        return taskRepository.save(taskDb.get());
    }
}

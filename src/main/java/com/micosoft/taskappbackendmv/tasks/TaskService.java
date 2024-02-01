package com.micosoft.taskappbackendmv.tasks;

import com.micosoft.taskappbackendmv.errors.NotFoundException;
import com.micosoft.taskappbackendmv.subtasks.SubTaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;
    private final SubTaskRepository subTaskRepository;


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


    public Task createTask(Task task) {
        return taskRepository.save(task);
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

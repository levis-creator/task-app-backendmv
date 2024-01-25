package com.micosoft.taskappbackendmv.tasks;

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


    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Long id) {
        boolean taskExist = taskRepository.existsById(id);
        if (taskExist) {
            return taskRepository.findById(id).get();
        } else {
            throw new IllegalStateException("task does not exist");
        }
    }

    public Task createTask(Task task) {
        boolean taskExist = taskRepository.existsById(task.getTaskId());
        if (taskExist) {
            throw new IllegalStateException("task already exist");
        } else {
            return taskRepository.save(task);
        }
    }

    public String deleteTask(Long id) {
        boolean taskExist = taskRepository.existsById(id);
        if (taskExist){
            taskRepository.deleteById(id);
            return "Deleted successfully";
        }else {
            throw new IllegalStateException("task does not exists");
        }
    }


    public Task updateTask(Long id, Task task) {
        Optional<Task> taskDb= taskRepository.findById(id);

        if (taskDb.isEmpty()){
            throw new IllegalStateException("task does not exists");
        }else if (!task.getTaskTitle().isEmpty()
                &&!task.getTaskTitle().equals(taskDb.get().getTaskTitle())){
            taskDb.get().setTaskTitle(task.getTaskTitle());
        }
//        Description
        if (!task.getTasKDescription().isEmpty()&&!task.getTasKDescription().equals(taskDb.get().getTasKDescription())){
            taskDb.get().setTasKDescription(task.getTasKDescription());
        }
//        tags
        if (!task.getTags().isEmpty()&&!task.getTags().equals(taskDb.get().getTags())){
            taskDb.get().setTags(task.getTags());
        }
////      category
//        if (!task.getCategory().isEmpty()
//                && !task.getCategory().equals(taskDb.get().getCategory())){
//            taskDb.get().setCategory(task.getCategory());
//        }
//       Due date
        if (task.getDueDate() != null &&!task.getDueDate().equals(taskDb.get().getDueDate())){
            taskDb.get().setDueDate(task.getDueDate());
        }

       return taskRepository.save(taskDb.get());
    }
}

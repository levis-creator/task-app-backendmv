package com.micosoft.taskappbackendmv.subtasks;

import com.micosoft.taskappbackendmv.errors.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubTaskService {
    @Autowired
    private final SubTaskRepository subTaskRepository;

    public List<SubTask> getSubTasks() {
        return subTaskRepository.findAll();
    }

    public SubTask getSubtask(Long id) {
        Optional<SubTask> subTaskDb = subTaskRepository.findById(id);
        if (subTaskDb.isPresent()) {
            return subTaskDb.get();
        } else {
            throw new NotFoundException("Subtask does not exist");
        }
    }

    public SubTask creatingSubTask(SubTask subTask) {
        Optional<SubTask> subTaskDb = subTaskRepository.findById(subTask.getSubTaskId());
        if (subTaskDb.isEmpty()) {
            return subTaskRepository.save(subTask);
        } else {
            throw new NotFoundException("Subtask already exist");
        }
    }

    public String deleteTask(Long id) {
        Optional<SubTask> subTaskDb = subTaskRepository.findById(id);
        if (subTaskDb.isPresent()) {
            subTaskRepository.deleteById(id);
            return "Delete subtask";
        } else {
            throw new NotFoundException("Task not found");
        }
    }

    public SubTask updateSubTask(Long id, SubTask subTask) {
        Optional<SubTask> subTaskDb = subTaskRepository.findById(id);
        if (subTaskDb.isEmpty()) {
            throw new NotFoundException("Task not found");
        } else if (!subTask.getSubTaskName().isEmpty() && !subTask.getSubTaskName().equals(subTaskDb.get().getSubTaskName())) {
            subTaskDb.get().setSubTaskName(subTask.getSubTaskName());
        }
        if (!subTask.isCompletedSubTask()==subTaskDb.get().isCompletedSubTask()) {
            subTaskDb.get().setCompletedSubTask(subTask.isCompletedSubTask());
        }
        return subTaskRepository.save(subTaskDb.get());
    }
}

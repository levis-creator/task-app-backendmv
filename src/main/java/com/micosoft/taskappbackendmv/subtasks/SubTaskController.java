package com.micosoft.taskappbackendmv.subtasks;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subtask")
public class SubTaskController {
    @Autowired
    private final SubTaskService subTaskService;

    @GetMapping
    public List<SubTask> getSubtasks() {
        return subTaskService.getSubTasks();
    }

    @GetMapping("{id}")
    public SubTask getSubtask(@PathVariable Long id) {
        return subTaskService.getSubtask(id);
    }

    @PostMapping
    public SubTask creatingSubTask(@RequestBody SubTask subTask) {
        return subTaskService.creatingSubTask(subTask);
    }

    @DeleteMapping("{id}")
    public String deletingSubTask(@PathVariable Long id) {
        return subTaskService.deleteTask(id);
    }

    @PutMapping("{id}")
    public SubTask updateSubTask(@PathVariable Long id, @RequestBody SubTask subTask) {
        return subTaskService.updateSubTask(id, subTask);
    }

}

package com.micosoft.taskappbackendmv.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    TaskRepository taskRepository;
    @InjectMocks
    TaskService taskService;
    Task task;
    Long taskId=1L;
    List<String> tag=new ArrayList<>();
    List<String> tagsList= Arrays.asList("Work","Emergency");
    List<String> newTagsList= Arrays.asList("Hobbies","Emergency");


    Task updateTask= task= Task.builder().taskId(1L)
            .taskTitle("Create something Else")
            .tags(newTagsList)

            .tasKDescription("Some description else")
            .dueDate(LocalDate.of(2019, Month.FEBRUARY, 27))
           .build();
    @BeforeEach
    void setUp() {
        tag.addAll(tagsList);
        task= Task.builder().taskId(1L).taskTitle("Create something").tags(tagsList).tasKDescription("Some description").dueDate(LocalDate.of(2019, Month.FEBRUARY, 20)).build();
    }
    @Test
    void gettingTaskList(){
        taskService.getTasks();
        verify(taskRepository).findAll();
    }
    @Test
    void gettingTaskByIdWhenTaskExists(){
        when(taskRepository.existsById(taskId)).thenReturn(true);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        assertThat( taskService.getTask(taskId)).isNotNull();
    }
    @Test
    void gettingTaskWhenTasKDoesNotExist(){
        when(taskRepository.existsById(taskId)).thenReturn(false);
        assertThrows(IllegalStateException.class,()->taskService.getTask(taskId));
    }
    @Test
    void creatingTaskWhenTaskExists(){
        when(taskRepository.existsById(taskId)).thenReturn(true);
        assertThrows(IllegalStateException.class,()->taskService.createTask(task));
        verify(taskRepository, never()).save(any());
    }
    @Test
    void  creatingTaskWhenTaskDoesNotExist(){
        when(taskRepository.existsById(taskId)).thenReturn(false);
        taskService.createTask(task);
        verify(taskRepository).save(task);
    }
    @Test
    void  deletingTaskThatExist(){
        when(taskRepository.existsById(taskId)).thenReturn(true);taskService.deleteTask(taskId);
        assertThat(taskService.deleteTask(taskId)).isNotEmpty();
    }
    @Test
    void  deletingTaskThatDoesNotExist(){
        when(taskRepository.existsById(taskId)).thenReturn(false);
        assertThrows(IllegalStateException.class, ()->taskService.deleteTask(taskId));
    }
    @Test
    void updateTaskThatDoesNotExist(){

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class,()->taskService.updateTask(taskId, updateTask));

    }
    @Test
    void updateTaskThatExists(){
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        Task result=taskService.updateTask(taskId, updateTask);
        assertAll(
                ()->assertEquals(updateTask.getTaskTitle(),result.getTaskTitle()),
                ()->assertEquals(updateTask.getTasKDescription(),result.getTasKDescription()),
                ()->assertEquals(updateTask.getTags(),result.getTags()),
                ()->assertEquals(updateTask.getDueDate(),result.getDueDate())
        );

    }






}
package com.micosoft.taskappbackendmv.subtasks;

import com.micosoft.taskappbackendmv.errors.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubTaskServiceTest {
    @Mock
    SubTaskRepository subTaskRepository;
    @InjectMocks
    SubTaskService subTaskService;

    SubTask subTask;
    Long subTaskId = 1L;


    SubTask updateSubTask = SubTask.builder().subTaskId(1L).subTaskName("Make Something else").completedSubTask(true).build();

    @BeforeEach
    void setUp() {
        subTask = SubTask.builder().subTaskId(1L).subTaskName("Make Something").completedSubTask(false).build();
    }

    @Test
    void gettingAllSubtask() {
        subTaskService.getSubTasks();
        verify(subTaskRepository).findAll();
    }

    @Test
    void gettingSingleSubTaskWhenTaskIsFound() {
        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.of(subTask));
        assertThat(subTaskService.getSubtask(subTaskId)).isNotNull();
    }

    @Test
    void gettingSingleSubTaskIsNotFound() {
        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> subTaskService.getSubtask(subTaskId));
    }
    @Test
    void creatingSubTaskWhenDoesNotExist(){
        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.empty());
        subTaskService.creatingSubTask(subTask);
        verify(subTaskRepository).save(subTask);
    }
    @Test
    void creatingSubTaskWhenExist(){
        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.of(subTask));
        assertThrows(NotFoundException.class,()->subTaskService.creatingSubTask(subTask));
        verify(subTaskRepository, never()).save(any());
    }
    @Test
    void deletingSubTaskWhenDoesNotExist(){
        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()->subTaskService.deleteTask(subTaskId));
        verify(subTaskRepository, never()).deleteById(subTaskId);
    }
    @Test
    void deletingSubTaskExist(){
        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.of(subTask));
        assertThat(subTaskService.deleteTask(subTaskId)).isNotEmpty();
        verify(subTaskRepository).deleteById(subTaskId);
    }
    @Test
    void updatingSubTaskThatDoesNotExist(){
        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()->subTaskService.updateSubTask(subTaskId, updateSubTask));
        verify(subTaskRepository, never()).save(updateSubTask);
    }
    @Test
    void  updatingSubTaskThatExis(){
        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.of(subTask));
        when(subTaskRepository.save(subTask)).thenReturn(subTask);
        SubTask result= subTaskService.updateSubTask(subTaskId, updateSubTask);
        assertAll(
                ()->assertEquals(updateSubTask.getSubTaskName(), result.getSubTaskName()),
                ()->assertEquals(updateSubTask.isCompletedSubTask(),result.isCompletedSubTask())

        );

    }

}
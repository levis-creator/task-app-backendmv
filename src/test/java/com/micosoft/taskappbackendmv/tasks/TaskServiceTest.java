package com.micosoft.taskappbackendmv.tasks;

import com.micosoft.taskappbackendmv.categories.Category;
import com.micosoft.taskappbackendmv.errors.NotFoundException;
import com.micosoft.taskappbackendmv.subtasks.SubTask;
import com.micosoft.taskappbackendmv.tags.Tags;
import com.micosoft.taskappbackendmv.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
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
    Long taskId = 1L;
    List<String> tag = new ArrayList<>();
    Tags tagInstance = new Tags(1L, "Emergency", "#ffff", null);
    Tags newTagInstance = new Tags(1L, "Emergency updates", "#ffff", null);
    SubTask subTask = new SubTask(1L, "Something", false);
    List<SubTask> subTaskList = new ArrayList<>(List.of(subTask));
    Collection<Tags> tagsList = new ArrayList<>(List.of(tagInstance));
    Collection<Tags> newTagsList = new ArrayList<>(List.of(newTagInstance));
    Task updateTask = Task.builder().taskId(1L)
            .taskName("Create something Else")
//            .tags(newTagsList)
            .subTasks(null)
            .taskDescription("Some description else")
            .dueDate(LocalDate.of(2019, Month.FEBRUARY, 27))
            .user(null).categoryItem(null).build();

    @BeforeEach
    void setUp() {
        task = Task.builder().taskId(1L)
                .subTasks(subTaskList)
                .taskName("Create something")
//                .tags(tagsList)
                .taskDescription("Some description").
                dueDate(LocalDate.of(2019, Month.FEBRUARY, 20)).user(new User("oafarg","username","password", "username@email.com")).categoryItem(new Category()).build();
    }

    @Test
    void gettingTaskList() {
        taskService.getTasks();
        verify(taskRepository).findAll();
    }

    @Test
    void gettingTaskByIdWhenTaskExists() {
        when(taskRepository.existsById(taskId)).thenReturn(true);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        assertThat(taskService.getTask(taskId)).isNotNull();
    }

    @Test
    void gettingTaskWhenTasKDoesNotExist() {
        when(taskRepository.existsById(taskId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> taskService.getTask(taskId));
    }


    @Test
    void creatingTaskWhenTaskDoesNotExist() {
        taskService.createTask(task);
        verify(taskRepository).save(task);
    }

    @Test
    void deletingTaskThatExist() {
        when(taskRepository.existsById(taskId)).thenReturn(true);
        taskService.deleteTask(taskId);
        assertThat(taskService.deleteTask(taskId)).isNotEmpty();
    }

    @Test
    void deletingTaskThatDoesNotExist() {
        when(taskRepository.existsById(taskId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> taskService.deleteTask(taskId));
    }

    @Test
    void updateTaskThatDoesNotExist() {

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> taskService.updateTask(taskId, updateTask));

    }

    @Test
    void updateTaskThatExists() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        Task result = taskService.updateTask(taskId, updateTask);
        assertAll(
                () -> assertEquals(updateTask.getTaskName(), result.getTaskName()),
                () -> assertEquals(updateTask.getTaskDescription(), result.getTaskDescription()),
//                () -> assertEquals(updateTask.getTags(), result.getTags()),
                () -> assertEquals(updateTask.getDueDate(), result.getDueDate())
        );

    }


}
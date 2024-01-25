package com.micosoft.taskappbackendmv.subtasks;

import com.micosoft.taskappbackendmv.tasks.Task;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sub_task")
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long subTaskId;
    private String subTaskName;
    private boolean completedSubTask;

    @ManyToOne
    @JoinColumn(name = "task_task_id")
    private Task task;

}
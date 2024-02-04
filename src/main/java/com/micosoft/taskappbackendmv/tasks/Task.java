package com.micosoft.taskappbackendmv.tasks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.micosoft.taskappbackendmv.categories.Category;
import com.micosoft.taskappbackendmv.subtasks.SubTask;
import com.micosoft.taskappbackendmv.tags.Tags;
import com.micosoft.taskappbackendmv.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Builder
@Getter
@Setter
@Entity
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long taskId;
    private String taskName;
    private String taskDescription;
//    private List<Tags> tags;

    private LocalDate dueDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToMany
//    @JoinTable(name = "task_tags",
//            joinColumns = @JoinColumn(name = "task_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id"))
//    private Collection<Tags> tags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private Collection<SubTask> subTasks = new ArrayList<>();
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn
    private Category category;

    public Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }
}
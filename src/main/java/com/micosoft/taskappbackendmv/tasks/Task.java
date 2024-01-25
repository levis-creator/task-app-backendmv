package com.micosoft.taskappbackendmv.tasks;

import com.micosoft.taskappbackendmv.categories.Category;
import com.micosoft.taskappbackendmv.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long taskId;
    private String taskTitle;
    private String tasKDescription;
    private List<String> tags;
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_item_id")
    private Category categoryItem;

}
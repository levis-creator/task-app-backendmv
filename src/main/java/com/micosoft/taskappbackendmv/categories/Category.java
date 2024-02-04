package com.micosoft.taskappbackendmv.categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.micosoft.taskappbackendmv.tasks.Task;
import com.micosoft.taskappbackendmv.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long categoryId;
    private String categoryName;
    private String categoryColor;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

}
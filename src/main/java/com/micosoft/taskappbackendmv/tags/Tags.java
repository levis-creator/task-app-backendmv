package com.micosoft.taskappbackendmv.tags;

import com.micosoft.taskappbackendmv.tasks.Task;
import com.micosoft.taskappbackendmv.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long tagId;
    private String tagName;
    private  String colors;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @ManyToMany(mappedBy = "tags")
    private Set<Task> tasks = new LinkedHashSet<>();

}
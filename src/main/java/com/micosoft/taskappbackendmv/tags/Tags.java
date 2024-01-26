package com.micosoft.taskappbackendmv.tags;

import com.micosoft.taskappbackendmv.users.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
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
    @JoinColumn(name = "user_user_id")
    private User user;

}
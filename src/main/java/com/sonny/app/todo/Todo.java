package com.sonny.app.todo;

import com.sonny.app.category.Category;
import com.sonny.app.common.BaseEntity;
import com.sonny.app.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TODOS")
public class Todo extends BaseEntity {

    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    @Column(nullable = false)
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
}

package com.sonny.app.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, String> {

    @Query("""
            select t from Todo t
            where t.user.id = :userId
            and t.startDate = current_date
            """)
    List<Todo> findAllByUserId(String userId);

    List<Todo> findAllByUserIdAndCategoryId(String userId, String categoryId);

    @Query("""
           SELECT t FROM Todo t
           WHERE t.endDate >= CURRENT_DATE and t.endTime >= CURRENT_TIME
           """)
    List<Todo> findAllDueTodos(String userId);
}

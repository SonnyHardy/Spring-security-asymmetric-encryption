package com.sonny.app.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("""
            select count (c) > 0
            from Category c
            where lower(c.name) = lower(:name)
            and c.createdBy = :userId or c.createdBy = 'Admin'
            """)
    boolean findByNameAndUser(String name, String userId);

    @Query("""
            select c from Category c
            where c.createdBy = :userId or c.createdBy = 'Admin'
            """)
    List<Category> findAllByUserId(String userId);

    @Query("""
            select c from Category c
            where c.id = :categoryId
            and (c.createdBy = :userId or c.createdBy = 'Admin')
            """)
    Optional<Category> findByIdAndUserId(String categoryId, String userId);
}

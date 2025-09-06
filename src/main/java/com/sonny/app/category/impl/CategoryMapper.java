package com.sonny.app.category.impl;

import com.sonny.app.category.Category;
import com.sonny.app.category.request.CategoryRequest;
import com.sonny.app.category.request.CategoryUpdateRequest;
import com.sonny.app.category.response.CategoryResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public Category toCategory(CategoryRequest request) {
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public void mergeCategory(Category categoryToUpdate, CategoryUpdateRequest request) {
        if (StringUtils.isNotBlank(request.getName())
        && !categoryToUpdate.getName().equals(request.getName())) {
            categoryToUpdate.setName(request.getName());
        }

        if (StringUtils.isNotBlank(request.getDescription())
        && !categoryToUpdate.getDescription().equals(request.getDescription())) {
            categoryToUpdate.setDescription(request.getDescription());
        }
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .todoCount(category.getTodos().size())
                .build();
    }
}

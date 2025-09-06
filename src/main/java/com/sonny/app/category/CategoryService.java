package com.sonny.app.category;

import com.sonny.app.category.request.CategoryRequest;
import com.sonny.app.category.request.CategoryUpdateRequest;
import com.sonny.app.category.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    String createCategory(CategoryRequest request, String userId);

    void updateCategory(CategoryUpdateRequest request, String categoryId, String userId);

    List<CategoryResponse> findAllByOwner(String userId);

    CategoryResponse findCategoryById(String categoryId);

    void deleteCategoryById(String categoryId);
}

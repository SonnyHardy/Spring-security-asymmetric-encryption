package com.sonny.app.category.impl;

import com.sonny.app.category.Category;
import com.sonny.app.category.CategoryRepository;
import com.sonny.app.category.CategoryService;
import com.sonny.app.category.request.CategoryRequest;
import com.sonny.app.category.request.CategoryUpdateRequest;
import com.sonny.app.category.response.CategoryResponse;
import com.sonny.app.exception.BusinessException;
import com.sonny.app.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public String createCategory(CategoryRequest request, String userId) {
        checkCategoryUnicityForUser(request.getName(), userId);

        final Category category = this.categoryMapper.toCategory(request);
        log.info("Creating new category: {}", category);
        return this.categoryRepository.save(category).getId();
    }

    @Override
    public void updateCategory(CategoryUpdateRequest request, String categoryId, String userId) {
        final Category categoryToUpdate = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

        checkCategoryUnicityForUser(request.getName(), userId);

        this.categoryMapper.mergeCategory(categoryToUpdate, request);
        log.info("Updating category: {}", categoryToUpdate);
        this.categoryRepository.save(categoryToUpdate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllByOwner(String userId) {
        return this.categoryRepository.findAllByUserId(userId)
                .stream()
                .map(this.categoryMapper::toCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse findCategoryById(String categoryId) {
        return this.categoryRepository.findById(categoryId)
                .map(this.categoryMapper::toCategoryResponse)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
    }

    @Override
    public void deleteCategoryById(String categoryId) {
        // Todo: implement deletion later
        // Mark category for deletion
        // The scheduler should pick up all the marked categories and perform the deletion

    }

    private void checkCategoryUnicityForUser(String name, String userId) {
        final boolean alreadyExistsForUSer = this.categoryRepository.findByNameAndUser(name, userId);
        if (alreadyExistsForUSer) throw new BusinessException(ErrorCode.CATEGORY_ALREADY_EXISTS_FOR_USER, name);
    }

}

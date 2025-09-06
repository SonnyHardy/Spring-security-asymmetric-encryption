package com.sonny.app.category;

import com.sonny.app.category.request.CategoryRequest;
import com.sonny.app.category.request.CategoryUpdateRequest;
import com.sonny.app.category.response.CategoryResponse;
import com.sonny.app.common.RestResponse;
import com.sonny.app.user.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Category API")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<RestResponse> createCategory(
            @RequestBody @Valid
            CategoryRequest request,
            Authentication authentication
    ) {
        final String userId = ((User) authentication.getPrincipal()).getId();
        final String categoryId = this.categoryService.createCategory(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponse(categoryId));
    }

    @PutMapping("/{category-id}")
    public ResponseEntity<Void> updateCategory(
            @RequestBody @Valid
            final CategoryUpdateRequest request,
            @PathVariable("category-id")
            final String categoryId,
            final Authentication authentication
            ) {
        final String userId = ((User) authentication.getPrincipal()).getId();
        this.categoryService.updateCategory(request, categoryId, userId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAllCategories(
            final Authentication authentication
    ) {
        final String userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(this.categoryService.findAllByOwner(userId));
    }

    @GetMapping("/{category-id}")
    public ResponseEntity<CategoryResponse> findCategoryById(
            @PathVariable("category-id")
            final String categoryId
    ) {
        return ResponseEntity.ok(this.categoryService.findCategoryById(categoryId));
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity<Void> deleteCategoryById(
            @PathVariable("category-id")
            final String categoryId
    ) {
        this.categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.ok().build();
    }

}

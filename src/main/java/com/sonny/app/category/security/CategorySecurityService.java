package com.sonny.app.category.security;

import com.sonny.app.category.Category;
import com.sonny.app.category.CategoryRepository;
import com.sonny.app.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategorySecurityService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public boolean isCategoryOwner(final String categoryId) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String userId = ((User) authentication.getPrincipal()).getId();
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("No category found with id: " + categoryId));

        log.debug("isCategoryOwner: {}, userId: {}", category.getCreatedBy(), userId);
        return category.getCreatedBy().equals(userId);
    }
}

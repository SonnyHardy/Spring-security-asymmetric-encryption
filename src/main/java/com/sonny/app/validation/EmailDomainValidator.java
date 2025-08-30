package com.sonny.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmailDomainValidator implements ConstraintValidator<NonDisposableEmail, String> {

    private final Set<String> blocked;

    public EmailDomainValidator(
            @Value("${app.security.disposable-email}")
            List<String> domains
    ) {
        this.blocked = domains.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String email, final ConstraintValidatorContext context) {
        if (email == null || !email.contains("@")) {
            return true; // Let @Email handle null or invalid format
        }
        final int atIndex = email.indexOf("@") + 1;
        final int dotIndex = email.lastIndexOf(".");
        final String domain = email.substring(atIndex, dotIndex).toLowerCase();
        return !this.blocked.contains(domain); // Return true if domain is not in the blocked list
    }
}

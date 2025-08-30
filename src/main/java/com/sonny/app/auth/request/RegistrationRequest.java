package com.sonny.app.auth.request;

import com.sonny.app.validation.NonDisposableEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {

    @NotBlank(message = "VALIDATION.REGISTRATION.FIRST_NAME.NOT_BLANK")
    @Size(min = 3, max = 255, message = "VALIDATION.REGISTRATION.FIRST_NAME.SIZE")
    @Pattern(regexp = "^[\\p{L} '-]+$",  // Allows letters (including accented), spaces, apostrophes, and hyphens
            message = "VALIDATION.REGISTRATION.FIRST_NAME.PATTERN"
    )
    @Schema(example = "John")
    private String firstName;

    @NotBlank(message = "VALIDATION.REGISTRATION.LAST_NAME.NOT_BLANK")
    @Size(min = 3, max = 255, message = "VALIDATION.REGISTRATION.LAST_NAME.SIZE")
    @Pattern(regexp = "^[\\p{L} '-]+$",  // Allows letters (including accented), spaces, apostrophes, and hyphens
            message = "VALIDATION.REGISTRATION.LAST_NAME.PATTERN"
    )
    @Schema(example = "Doe")
    private String lastName;

    @NotBlank(message = "VALIDATION.REGISTRATION.EMAIL.NOT_BLANK")
    @Email(message = "VALIDATION.REGISTRATION.EMAIL.FORMAT")
    @NonDisposableEmail(message = "VALIDATION.REGISTRATION.EMAIL.DISPOSABLE") // Custom annotation to check for disposable email domains
    @Schema(example = "john@gmail.com")
    private String email;

    @NotBlank(message = "VALIDATION.REGISTRATION.PASSWORD.NOT_BLANK")
    @Size(min = 8, max = 255, message = "VALIDATION.REGISTRATION.PASSWORD.SIZE")
    @Pattern(
            // At least one uppercase letter, one lowercase letter, one digit, and one special character
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "VALIDATION.REGISTRATION.PASSWORD.FORMAT"
    )
    @Schema(example = "P@ssw0rd!56")
    private String password;

    @NotBlank(message = "VALIDATION.REGISTRATION.CONFIRM_PASSWORD.NOT_BLANK")
    @Size(min = 8, max = 255, message = "VALIDATION.REGISTRATION.CONFIRM_PASSWORD.SIZE")
    @Schema(example = "P@ssw0rd!56")
    private String confirmPassword;

    @NotBlank(message = "VALIDATION.REGISTRATION.PHONE_NUMBER.NOT_BLANK")
    @Pattern(
            regexp = "^\\+?[0-9]\\d{9,15}$", // E.164 format: allows optional '+' followed by country code and subscriber number
            message = "VALIDATION.REGISTRATION.PHONE_NUMBER.FORMAT"
    )
    @Schema(example = "+1234567890")
    private String phoneNumber;
}

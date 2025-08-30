package com.sonny.app.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {
    @NotBlank(message = "VALIDATION.AUTHENTICATION.EMAIL_NOT_BLANK")
    @Email(message = "VALIDATION.AUTHENTICATION.EMAIL_INVALID")
    @Schema(example = "sonny@gmail.com")
    private String email;

    @NotBlank(message = "VALIDATION.AUTHENTICATION.PASSWORD_NOT_BLANK")
    @Schema(example = "P@ssw0rd!34")
    private String password;
}

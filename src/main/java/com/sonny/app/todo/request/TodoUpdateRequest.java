package com.sonny.app.todo.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoUpdateRequest {

    @NotBlank(message = "VALIDATION.TODO.TITLE.NOT_BLANK")
    private String title;
    private String description;
    @FutureOrPresent(message = "VALIDATION.TODO.START_DATE.FUTURE_OR_PRESENT")
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String categoryId;
}

package com.sonny.app.category.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {

    private String id;
    private String name;
    private String description;
    private int todoCount;
}

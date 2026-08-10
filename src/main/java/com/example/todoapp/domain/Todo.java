package com.example.todoapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {

    private Long id;
    private Long memberId;
    private Long categoryId;
    private String title;
    private String content;
    private LocalDate dueDate;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * MyBatis resultMap의 <association>으로 조인되어 채워지는 필드.
     * category_id, category_name 컬럼을 Category 객체로 매핑한다.
     */
    private Category category;

}

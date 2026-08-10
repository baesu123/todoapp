package com.example.todoapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TodoForm {

    private Long id;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    private String content;

    private LocalDate dueDate;

    private Long categoryId;

    private boolean completed;

}

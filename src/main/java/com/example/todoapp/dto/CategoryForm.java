package com.example.todoapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryForm {

    @NotBlank(message = "카테고리 이름을 입력해주세요.")
    private String name;

}

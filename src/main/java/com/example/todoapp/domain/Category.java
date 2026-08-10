package com.example.todoapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * memberId가 null이면 모든 회원이 공용으로 사용하는 기본 카테고리,
 * 값이 있으면 해당 회원이 직접 추가한 개인 카테고리.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    private Long id;
    private Long memberId;
    private String name;
    private LocalDateTime createdAt;

}

package com.example.todoapp.mapper;

import com.example.todoapp.domain.Todo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TodoMapper {

    /**
     * category와 LEFT JOIN 하여 카테고리명까지 함께 조회 (resultMap의 association 사용)
     * categoryId, completed는 선택적 필터 (null이면 전체)
     */
    List<Todo> findAllByMember(
            @Param("memberId") Long memberId,
            @Param("categoryId") Long categoryId,
            @Param("completed") Boolean completed
    );

    Todo findByIdAndMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

    void insert(Todo todo);

    int update(Todo todo);

    int deleteByIdAndMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

    int toggleCompleted(@Param("id") Long id, @Param("memberId") Long memberId);

}

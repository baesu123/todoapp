package com.example.todoapp.mapper;

import com.example.todoapp.domain.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 공용 카테고리(member_id IS NULL) + 해당 회원이 만든 개인 카테고리를 함께 조회
     */
    List<Category> findAllForMember(@Param("memberId") Long memberId);

    Category findById(@Param("id") Long id);

    void insert(Category category);

    /**
     * 본인이 만든 카테고리만 삭제 가능 (공용 카테고리는 memberId 조건에 안 걸려 삭제 안 됨)
     */
    int deleteByIdAndMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

}

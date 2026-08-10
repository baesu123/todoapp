package com.example.todoapp.service;

import com.example.todoapp.domain.Category;
import com.example.todoapp.dto.CategoryForm;
import com.example.todoapp.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;

    public List<Category> findAllForMember(Long memberId) {
        return categoryMapper.findAllForMember(memberId);
    }

    @Transactional
    public void create(Long memberId, CategoryForm form) {
        Category category = Category.builder()
                .memberId(memberId)
                .name(form.getName())
                .build();
        categoryMapper.insert(category);
    }

    @Transactional
    public void delete(Long id, Long memberId) {
        int deleted = categoryMapper.deleteByIdAndMemberId(id, memberId);
        if (deleted == 0) {
            // 공용 카테고리이거나 남의 카테고리인 경우 삭제되지 않음
            throw new IllegalArgumentException("삭제할 수 없는 카테고리입니다.");
        }
    }

}

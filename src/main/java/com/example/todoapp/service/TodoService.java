package com.example.todoapp.service;

import com.example.todoapp.domain.Todo;
import com.example.todoapp.dto.TodoForm;
import com.example.todoapp.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoMapper todoMapper;

    public List<Todo> findAllByMember(Long memberId, Long categoryId, Boolean completed) {
        return todoMapper.findAllByMember(memberId, categoryId, completed);
    }

    public Todo getOwnedTodo(Long id, Long memberId) {
        Todo todo = todoMapper.findByIdAndMemberId(id, memberId);
        if (todo == null) {
            throw new IllegalArgumentException("존재하지 않거나 접근 권한이 없는 할일입니다.");
        }
        return todo;
    }

    @Transactional
    public void create(Long memberId, TodoForm form) {
        Todo todo = Todo.builder()
                .memberId(memberId)
                .categoryId(form.getCategoryId())
                .title(form.getTitle())
                .content(form.getContent())
                .dueDate(form.getDueDate())
                .completed(false)
                .build();
        todoMapper.insert(todo);
    }

    @Transactional
    public void update(Long id, Long memberId, TodoForm form) {
        Todo todo = Todo.builder()
                .id(id)
                .memberId(memberId)
                .categoryId(form.getCategoryId())
                .title(form.getTitle())
                .content(form.getContent())
                .dueDate(form.getDueDate())
                .completed(form.isCompleted())
                .build();

        int updated = todoMapper.update(todo);
        if (updated == 0) {
            throw new IllegalArgumentException("존재하지 않거나 접근 권한이 없는 할일입니다.");
        }
    }

    @Transactional
    public void delete(Long id, Long memberId) {
        int deleted = todoMapper.deleteByIdAndMemberId(id, memberId);
        if (deleted == 0) {
            throw new IllegalArgumentException("존재하지 않거나 접근 권한이 없는 할일입니다.");
        }
    }

    @Transactional
    public void toggleCompleted(Long id, Long memberId) {
        int updated = todoMapper.toggleCompleted(id, memberId);
        if (updated == 0) {
            throw new IllegalArgumentException("존재하지 않거나 접근 권한이 없는 할일입니다.");
        }
    }

}

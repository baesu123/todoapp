package com.example.todoapp.controller;

import com.example.todoapp.config.MemberDetails;
import com.example.todoapp.domain.Todo;
import com.example.todoapp.dto.TodoForm;
import com.example.todoapp.service.CategoryService;
import com.example.todoapp.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final CategoryService categoryService;

    @GetMapping
    public String list(@AuthenticationPrincipal MemberDetails principal,
                        @RequestParam(required = false) String categoryId,
                        @RequestParam(required = false) String completed,
                        Model model) {
        Long memberId = principal.getMemberId();

        // select box에서 "전체"를 선택하면 빈 문자열("")이 넘어오므로 직접 null 처리
        Long categoryIdParam = (categoryId == null || categoryId.isBlank()) ? null : Long.valueOf(categoryId);
        Boolean completedParam = (completed == null || completed.isBlank()) ? null : Boolean.valueOf(completed);

        model.addAttribute("todos", todoService.findAllByMember(memberId, categoryIdParam, completedParam));
        model.addAttribute("categories", categoryService.findAllForMember(memberId));
        model.addAttribute("selectedCategoryId", categoryIdParam);
        model.addAttribute("selectedCompleted", completedParam);
        return "todo/list";
    }

    @GetMapping("/new")
    public String newForm(@AuthenticationPrincipal MemberDetails principal, Model model) {
        model.addAttribute("todoForm", new TodoForm());
        model.addAttribute("categories", categoryService.findAllForMember(principal.getMemberId()));
        return "todo/form";
    }

    @PostMapping
    public String create(@AuthenticationPrincipal MemberDetails principal,
                          @Valid @ModelAttribute TodoForm todoForm,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllForMember(principal.getMemberId()));
            return "todo/form";
        }
        todoService.create(principal.getMemberId(), todoForm);
        return "redirect:/todos";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@AuthenticationPrincipal MemberDetails principal,
                            @PathVariable Long id,
                            Model model) {
        Todo todo = todoService.getOwnedTodo(id, principal.getMemberId());

        TodoForm form = new TodoForm();
        form.setId(todo.getId());
        form.setTitle(todo.getTitle());
        form.setContent(todo.getContent());
        form.setDueDate(todo.getDueDate());
        form.setCategoryId(todo.getCategoryId());
        form.setCompleted(todo.isCompleted());

        model.addAttribute("todoForm", form);
        model.addAttribute("categories", categoryService.findAllForMember(principal.getMemberId()));
        return "todo/form";
    }

    @PostMapping("/{id}")
    public String update(@AuthenticationPrincipal MemberDetails principal,
                          @PathVariable Long id,
                          @Valid @ModelAttribute TodoForm todoForm,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllForMember(principal.getMemberId()));
            return "todo/form";
        }
        todoService.update(id, principal.getMemberId(), todoForm);
        return "redirect:/todos";
    }

    @PostMapping("/{id}/delete")
    public String delete(@AuthenticationPrincipal MemberDetails principal, @PathVariable Long id) {
        todoService.delete(id, principal.getMemberId());
        return "redirect:/todos";
    }

    @PostMapping("/{id}/toggle")
    public String toggle(@AuthenticationPrincipal MemberDetails principal, @PathVariable Long id) {
        todoService.toggleCompleted(id, principal.getMemberId());
        return "redirect:/todos";
    }

}

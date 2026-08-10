package com.example.todoapp.controller;

import com.example.todoapp.config.MemberDetails;
import com.example.todoapp.dto.CategoryForm;
import com.example.todoapp.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String list(@AuthenticationPrincipal MemberDetails principal, Model model) {
        model.addAttribute("categories", categoryService.findAllForMember(principal.getMemberId()));
        model.addAttribute("categoryForm", new CategoryForm());
        return "category/list";
    }

    @PostMapping
    public String create(@AuthenticationPrincipal MemberDetails principal,
                          @Valid @ModelAttribute CategoryForm categoryForm,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllForMember(principal.getMemberId()));
            return "category/list";
        }
        categoryService.create(principal.getMemberId(), categoryForm);
        return "redirect:/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@AuthenticationPrincipal MemberDetails principal, @PathVariable Long id) {
        categoryService.delete(id, principal.getMemberId());
        return "redirect:/categories";
    }

}

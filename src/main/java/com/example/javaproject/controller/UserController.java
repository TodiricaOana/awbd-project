package com.example.javaproject.controller;

import com.example.javaproject.exception.definition.EmailAlreadyUsedException;
import com.example.javaproject.mapper.UserMapper;
import com.example.javaproject.service.security.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.javaproject.dto.UserDto;
import com.example.javaproject.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @PostMapping
    public String create(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult) throws EmailAlreadyUsedException {
        if (bindingResult.hasErrors()) {
            return "registerForm";
        }

        userService.createUser(user);
        return "redirect:/login-form";
    }

    @GetMapping("/form")
    public String userForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            UserDto user = new UserDto();
            model.addAttribute("user", user);
            return "registerForm";
        }

        return "redirect:/index";
    }

    @GetMapping
    public String getAll(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(2);
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("fullName").ascending());

        model.addAttribute("usersPagination", userService.getAllUsers(pageable));
        return "users";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "userInfo";
    }

    @GetMapping("/current")
    public String getCurrent(Model model) {
        String username = jpaUserDetailsService.getCurrentUser().getUsername();
        model.addAttribute("user", userService.findUserByEmail(username));
        return "userInfoCurrent";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    @DeleteMapping("/delete/current/{id}")
    public String deleteCurrentById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/login-form";
    }

    @GetMapping("/form/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "updateUserForm";
    }

    @PutMapping("/{id}")
    public String updateCurrent(@PathVariable("id") Long id, @Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "updateUserForm";
        }

        userService.updateUser(userMapper.mapToEntity(user), id);
        return "redirect:/users/current";
    }
}

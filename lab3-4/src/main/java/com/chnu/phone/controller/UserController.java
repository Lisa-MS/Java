package com.chnu.phone.controller;


import com.chnu.phone.entity.User;
import com.chnu.phone.entity.Utility;
import com.chnu.phone.entity.enums.UserStatus;
import com.chnu.phone.repository.UserRepository;
import com.chnu.phone.service.UserService;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping({"/user", "/"})
public class UserController {

    private UserService userService;

    @GetMapping
    public String all(Model model) {
        model.addAttribute("users", userService.all());
        return "user";
    }

    @GetMapping(value = "/create")
    public String getCreate(Model model) {
        User user = new User();
        user.setStatus(UserStatus.APPROVED);
        model.addAttribute("user", user);
        model.addAttribute("userStatus", UserStatus.values());
        return "addUser";
    }


    @PostMapping(value = "/edit")
    public String edit(Model model, User user) {
        try {
            model.addAttribute("user", userService.saveOrUpdate(user));
        }
        catch (DataIntegrityViolationException ex){
            model.addAttribute("user", user);
            model.addAttribute("userStatus", UserStatus.values());
            model.addAttribute("error", "User with such identifier already exist");
            return "addUser";
        }
        return "redirect:/user";
    }

    @GetMapping(value = "/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.byId(id));
        model.addAttribute("userStatus", UserStatus.values());
        return "addUser";
    }


}

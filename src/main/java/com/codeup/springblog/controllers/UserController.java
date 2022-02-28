package com.codeup.springblog.controllers;

import com.codeup.springblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
        private UserRepository userDao;

    public UserController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/username/{id}")
    public String showUsers(@PathVariable long id, Model model) {
        model.addAttribute("username", userDao.getById((int) id).getUsername());
//        model.addAttribute("chosenUser", userDao.getById((int) id).getUsername());
        return "users";
    }

}

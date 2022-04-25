package com.example.customertsnode.controller;

import com.example.customertsnode.entity.TypeOfPayment;
import com.example.customertsnode.entity.User;
import com.example.customertsnode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
public class MainController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String homePage() {
        return "home";
    }


    @GetMapping("/authenticated")
    public String pageForAuthUsers(Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return user.getEmail() + " " + user.getUsername();
    }

    @GetMapping("/admin")
    public String adminPage(Principal principal) {
        User user = userService.findByUsername(principal.getName());

        return user.getTypesOfPayment().stream().map(TypeOfPayment::getType).collect(Collectors.joining(","));
    }
}

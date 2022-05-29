package com.example.customertsnode.controller;

import com.example.customertsnode.entity.TypeOfPayment;
import com.example.customertsnode.entity.User;
import com.example.customertsnode.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.stream.Collectors;



@Controller
@Log
public class MainController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String homePage()
    {
        log.info("opened main page");
        return "main";
    }


}

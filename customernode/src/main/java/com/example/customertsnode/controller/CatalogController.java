package com.example.customertsnode.controller;

import com.example.customertsnode.CustomertsNodeApplication;
import com.example.customertsnode.entity.TypeOfPayment;
import com.example.customertsnode.entity.User;
import com.example.customertsnode.pojo.Offer;
import com.example.customertsnode.service.UserService;
import com.example.customertsnode.util.Sender;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;


@Controller
public class CatalogController {

    private static final Logger logger =  LogManager.getLogger(CustomertsNodeApplication.class.getName());

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @GetMapping("/catalog")
    public String catalogPage(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        List<TypeOfPayment> data = user.getTypesOfPayment();
        ResponseEntity<String> responseEntity = Sender.send(data, "http://localhost:8081/api/payment/");
        logger.info("was sended request on url {} with data {} ","http://localhost:8081/api/payment/", data.toString());
        if (responseEntity == null) return "catalog";//throw exception

        Offer[] offers = gson.fromJson(responseEntity.getBody(), Offer[].class);

        List<Offer> list = new ArrayList(List.of(offers));
        logger.info("came response with info {}", list.toString());
        model.addAttribute("offers", list);
        return "catalog";
    }
    //list<>

    @PostMapping("/catalog")
    public String addItem(Principal principal, HttpServletRequest request) {
        User user = userService.findByUsername(principal.getName());
        Map<String, String> data = new HashMap();
        data.put("offer",request.getParameter("id"));
        data.put("user", String.valueOf(user.getId()));
        HttpEntity<String> response = Sender.send(data, "http://localhost:8082/api/cart/add");
        logger.info("was sended request on url {} with data {} ","http://localhost:8082/api/cart/add", data.toString());

        logger.info("came response with info {}", response);
        return "redirect:/catalog";
    }
}

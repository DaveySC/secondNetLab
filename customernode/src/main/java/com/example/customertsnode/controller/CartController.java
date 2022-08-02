package com.example.customertsnode.controller;

import com.example.customertsnode.CustomertsNodeApplication;
import com.example.customertsnode.entity.User;
import com.example.customertsnode.pojo.Offer;
import com.example.customertsnode.service.UserService;
import com.example.customertsnode.util.Sender;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.*;

@Controller
public class CartController {

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

    @GetMapping("/cart")
    public String catalogPage(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        HashMap<String, String> data = new HashMap<>();
        data.put("user", String.valueOf(user.getId()));
        logger.info("was sended request on url {} with data {} ","http://localhost:8082/api/get/cart", data.toString());

        ResponseEntity<String> response = Sender.send(data,"http://localhost:8082/api/get/cart");
        String[] offersId = gson.fromJson(response.getBody(), String[].class);


        if (offersId == null) return "cart";
        logger.info("came response with info {}", offersId.length);
        response = Sender.send(offersId, "http://localhost:8081/api/get/offers");
        logger.info("was sended request on url {} with data {} ","http://localhost:8081/api/get/offers", data.toString());
        Offer[] offers = gson.fromJson(response.getBody(), Offer[].class);
        List<Offer> list = new ArrayList(List.of(offers));
        logger.info("came response with info {}", list.toString());
        HashMap<Offer, Long> map = new HashMap<>();
        Collections.sort(list, new Comparator<Offer>() {
            @Override
            public int compare(Offer o1, Offer o2) {
                return Long.compare(o1.getId(), o2.getId());
            }
        });
        long count = 1;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).equals(list.get(i-1))) {
                count += 1;
            } else {
                map.put(list.get(i - 1), count);
                count = 1;
            }
        }
        if (!map.containsKey(list.get(list.size() - 1))) {
            map.put(list.get(list.size() - 1), count);
        }

        model.addAttribute("offers", map);
        logger.info("was added into model data : {}", map.toString());
        System.out.println(map);
        return "cart";
    }


    @PostMapping("/cart")
    public String buy(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        String data = String.valueOf(user.getId());
        Sender.send(data, "http://localhost:8082/api/buy");
        logger.info("was sended request on url {} with data {} ","http://localhost:8082/api/buy", data.toString());
        return "redirect:/account";
    }



}












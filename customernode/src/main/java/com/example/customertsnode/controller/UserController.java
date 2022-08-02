package com.example.customertsnode.controller;


import com.example.customertsnode.CustomertsNodeApplication;
import com.example.customertsnode.entity.Role;
import com.example.customertsnode.entity.TypeOfPayment;
import com.example.customertsnode.entity.User;
import com.example.customertsnode.pojo.Offer;
import com.example.customertsnode.repository.TypeOfPaymentRepository;
import com.example.customertsnode.repository.UserRepository;
import com.example.customertsnode.service.TypeOfPaymentService;
import com.example.customertsnode.service.UserService;
import com.example.customertsnode.util.Sender;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.catalina.connector.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.security.Principal;
import java.util.*;

@Controller
public class UserController {

    private static final Logger logger =  LogManager.getLogger(CustomertsNodeApplication.class.getName());

    private TypeOfPaymentService typeOfPaymentService;

    @Autowired
    public void setTypeOfPaymentService(TypeOfPaymentService typeOfPaymentService) {
        this.typeOfPaymentService = typeOfPaymentService;
    }

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

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

    @GetMapping("/registration")
    public String returnRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String firstname,
                             @RequestParam String lastname,
                             @RequestParam String email) {
        if (userService.findByUsername(username) != null) {
            //error
        }
        User user = new User(username, email, firstname, lastname, passwordEncoder.encode(password));
        userService.saveOrUpdateUser(user);
        logger.info("was created user : {}", user.toString());
        return "redirect:/login";
    }



    @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }


    @PostMapping("/logout")
    public String logout(Request request) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }


    @GetMapping("/account")
    public String accountPage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<TypeOfPayment> types = typeOfPaymentService.findAll();
        model.addAttribute("types", types);
        model.addAttribute("user", user);

        String data = String.valueOf(user.getId());
        String url = "http://localhost:8082/api/cart/getAll";
        logger.info("was sended request on url {} with data {} ",url, data.toString());
        HttpEntity<String> response = Sender.send(data, url);

        Type type = new TypeToken<List<Map<String,Long>>>(){}.getType();
        List<Map<String, Long>> ordersInfo = gson.fromJson(response.getBody(), type);
        if (ordersInfo == null ) return "account";
        logger.info("came response with info {}", ordersInfo.toString());
        model.addAttribute("orderInfo", ordersInfo);
        return "account";
    }

    @PostMapping("/account")
    public String updateTypes(@ModelAttribute("user") User user, Principal principal) {
        User userFromDB = userService.findByUsername(principal.getName());
        userFromDB.setTypesOfPayment(user.getTypesOfPayment());
        userService.saveOrUpdateUser(userFromDB);
        logger.info("was updated user's {} types of payment", userFromDB.toString());
        System.out.println(userFromDB.getTypesOfPayment());
        System.out.println(userFromDB);
        return "redirect:/account";
    }


    @GetMapping("/info")
    public String infoGet(Model model) {
        return "info";
    }


    @PostMapping("/info")
    public void infoPost( HttpServletRequest request, Model model) {
        Long id = Long.parseLong(request.getParameter("id"));
        HttpEntity<String> response = Sender.send(id,"http://localhost:8082/api/cart/offers");
        logger.info("was sended request on url {} with data {} ","http://localhost:8082/api/cart/offers", id.toString());
        String[] offers = gson.fromJson(response.getBody(), String[].class);
        logger.info("came response with info {}", Arrays.toString(offers));
        response = Sender.send(offers,"http://localhost:8081/api/get/offers");
        logger.info("was sended request on url {} with data {} ","http://localhost:8081/api/get/offers", id.toString());
        Offer[] info = gson.fromJson(response.getBody(), Offer[].class);
        logger.info("came response with info {}", Arrays.toString(info));
        List<Offer> list = new ArrayList(List.of(info));
        HashMap<Offer, Long> map = new HashMap<>();
        list.sort(new Comparator<Offer>() {
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

        infoGet(model);
    }
}

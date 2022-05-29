package com.example.ordernode.rest;

import com.example.ordernode.entity.Cart;
import com.example.ordernode.entity.Customer;
import com.example.ordernode.entity.Offer;
import com.example.ordernode.repository.CartRepository;
import com.example.ordernode.service.CustomerService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api")
public class RestApiController {

    private CustomerService customerService;

    @Autowired
    public void setUserService(CustomerService userService) {
        this.customerService = userService;
    }

    private Gson gson;


    private CartRepository cartRepository;

    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }


    //@Scheduled()
    @PostMapping("/cart/add")
    public String addItem(HttpEntity<Map<String, String>> httpEntity) {
        Long customerId = Long.valueOf(httpEntity.getBody().get("user"));
        Long offerId = Long.valueOf(httpEntity.getBody().get("offer"));
        System.out.println(customerId + " " + offerId);
        customerService.addOfferToCustomer(customerId, offerId);
        return "OK";
    }


    @PostMapping("/get/cart")
    public String getCart(HttpEntity<Map<String, String>> httpEntity) {
        Long customerId = Long.valueOf(httpEntity.getBody().get("user"));
        return gson.toJson(customerService.getCustomerOffers(customerId));
    }


    @PostMapping("/buy")
    public void buy(HttpEntity<String> httpEntity) {
        Long customerId = Long.parseLong(httpEntity.getBody());
        customerService.buyCart(customerId);
    }


    @PostMapping("/cart/getAll")
    public String getCarts(HttpEntity<String> httpEntity) {
        Long customerId = Long.parseLong(httpEntity.getBody());
        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) return null;
        return gson.toJson(customerService.getAllCarts(customerId));
    }

    @PostMapping("/cart/offers")
    public String getCartOffers(HttpEntity<String> httpEntity) {
        Long id = Long.parseLong(httpEntity.getBody());
        Cart cart = cartRepository.getById(id);
        return gson.toJson(cart.getOffers().stream().map(Offer::getId).map(String::valueOf).collect(Collectors.toList()));
    }
}

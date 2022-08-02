package com.example.ordernode.service;

import com.example.ordernode.entity.*;
import com.example.ordernode.repository.CartRepository;
import com.example.ordernode.repository.OfferRepository;
import com.example.ordernode.repository.CustomerRepository;
import com.example.ordernode.repository.TypeRepository;
import com.example.ordernode.util.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private TypeRepository typeRepository;

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    private CartRepository cartRepository;

    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    private OfferRepository offerRepository;

    @Autowired
    public void setOfferRepository(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    private CustomerRepository customerRepository;

    @Autowired
    public void setService(CustomerRepository repository) {
        this.customerRepository = repository;
    }

    public Customer findCustomerById(Long id) {return customerRepository.findCustomerById(id);}


    public void addOfferToCustomer(Long customerId, Long offerId) {

        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            customer = new Customer();
            customer.setId(customerId);
        }
        customerRepository.save(customer);
        Offer offer = offerRepository.findOfferById(offerId);
        if (offer == null) {
            offer = new Offer();
            offer.setId(offerId);
        }
        offerRepository.save(offer);
        Optional<Cart> optionalCart = customer.getActualCart();
        Cart cart = null;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = new Cart();
            cart.setType(typeRepository.findTypeByType(TypeEnum.ACTUAL.getType()));
            cartRepository.save(cart);
        }
        cart.addOffer(offer);
        customer.addCart(cart);
        customerRepository.save(customer);
    }

    public List<String> getCustomerOffers(Long customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) return null;
        Optional<Cart> optionalCart = customer.getActualCart();
        Cart cart = null;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            return null;
        }
        return cart.getOffers().stream().map(Offer::getId).map(String::valueOf).collect(Collectors.toList());
    }


    public void buyCart(Long customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        Optional<Cart> optionalCart = customer.getActualCart();
        Cart cart = optionalCart.get();
        cart.setType(typeRepository.findTypeByType(TypeEnum.ORDERED.getType()));

        customerRepository.save(customer);
    }

    public List<Map<String, Long>> getAllCarts(Long customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        List<Long> ids = customer.getOrderedCarts();

        List<Map<String, Long>> mapList = new ArrayList<>();

        for (Long id : ids) {
            Map<String, Long> map = new HashMap<>();
            Cart cart =  cartRepository.getById(id);
            map.put("Id", id);
            map.put("quantity", (long) cart.getOffers().size());

            HttpEntity<String> response =
                    Sender.send(cart.getOffers().stream().map(Offer::getId).collect(Collectors.toList()),
                            "http://localhost:8081/api/get/price");
            map.put("price", Long.parseLong(response.getBody()));
            mapList.add(map);
        }

        return mapList;
    }
}

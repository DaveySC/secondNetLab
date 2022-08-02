package com.example.ordernode.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Customer {

    @Column(name = "id")
    @Id
    Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<Cart> carts = new ArrayList<>();

    public void addCart(Cart cart) {
        this.carts.add(cart);
        cart.setCustomer(this);
    }

    public Optional<Cart> getActualCart() {
        return carts.stream().filter(e -> e.getType().getType().equals("actual")).findAny();
    }

    public List<Long> getOrderedCarts() {
        return carts.stream().filter(e -> e.getType().getType().equals("ordered")).map(Cart::getId).collect(Collectors.toList());
    }
}

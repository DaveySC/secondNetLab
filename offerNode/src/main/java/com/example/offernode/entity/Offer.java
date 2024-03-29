package com.example.offernode.entity;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Offer extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name="price")
    private Long price;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_of_payment_id")
    private TypeOfPayment typeOfPayment;




}

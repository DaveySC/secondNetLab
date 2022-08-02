package com.example.customertsnode.pojo;

import com.example.customertsnode.entity.BaseEntity;
import com.example.customertsnode.entity.TypeOfPayment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
public class Offer extends BaseEntity {

    private String name;

    private String description;

    private Long price;

    private TypeOfPayment typeOfPayment;


    @Override
    public boolean equals(Object o) {
        if (!o.getClass().equals(Offer.class)) return false;
        Offer offer = (Offer) o;
        return this.hashCode() == offer.hashCode();
    }

    @Override
    public int hashCode() {
        return (int)this.getId();
    }
}

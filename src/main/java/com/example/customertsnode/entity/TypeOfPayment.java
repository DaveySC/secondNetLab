package com.example.customertsnode.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "types_of_payment")
public class TypeOfPayment extends BaseEntity {

    @Column(name = "type")
    private String type;

    @ManyToMany(mappedBy = "typesOfPayment", fetch = FetchType.LAZY)
    private List<User> users;


    @Override
    public String toString() {
        return "TypeOfPayment{" +
                "type='" + type + '\'' +
                ", users=" + users.stream().map(User::getUsername).collect(Collectors.joining(" ")) +
                '}';
    }
}

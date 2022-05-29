package com.example.customertsnode.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "types_of_payment")
public class TypeOfPayment extends BaseEntity {

    @Column(name = "type")
    private String type;

    @JsonBackReference
    @ManyToMany(mappedBy = "typesOfPayment", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();



    @Override
    public String toString() {
        return "TypeOfPayment{" +
                "type='" + type + '\'' +
                "}";
    }
}

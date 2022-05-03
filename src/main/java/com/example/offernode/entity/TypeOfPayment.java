package com.example.offernode.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
public class TypeOfPayment extends BaseEntity{
    @Column(name = "type")
    private String type;


}

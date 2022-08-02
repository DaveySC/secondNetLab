package com.example.customertsnode.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
@DynamicUpdate
@DynamicInsert
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    @Column(name = "created", nullable = false, columnDefinition = "timestamp default now()")
    protected Date created = new Date();

    @LastModifiedDate
    @Column(name = "updated", nullable = false, columnDefinition = "timestamp default now()")
    protected Date updated = new Date();

    @JsonIgnore
    @Column(name = "status", nullable = false, columnDefinition = "varchar(25) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    protected Status status = Status.ACTIVE;

}